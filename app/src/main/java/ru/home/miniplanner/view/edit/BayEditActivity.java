package ru.home.miniplanner.view.edit;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.math.BigDecimal;

import ru.home.miniplanner.R;
import ru.home.miniplanner.Util;
import ru.home.miniplanner.db.HelperFactory;
import ru.home.miniplanner.model.Bay;

/**
 * Created by bespalov on 08.12.15.
 */
public class BayEditActivity extends EditActivity<Bay> {

    private EditText costEditText;
    private EditText dateRegEditText;
    private EditText descriptionEditText;

    @Override
    public Bay newInstanceEntity() {
        return new Bay();
    }

    @Override
    public void changeEntity() {
        entity.setCost(new BigDecimal(costEditText.getText().toString()));
        entity.setDateReg(Util.dateParse(dateRegEditText.getText().toString()));
        entity.setDescription(descriptionEditText.getText().toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dao = HelperFactory.getHelper().getBayDao();

        LinearLayout layout = (LinearLayout) findViewById(R.id.edit_content);
        getLayoutInflater().inflate(R.layout.edit_plan, layout, true);          // TODO добавить R.layout.edit_bay

        costEditText = (EditText) findViewById(R.id.costEditText);
        dateRegEditText = (EditText) findViewById(R.id.dateRegEditText);
        descriptionEditText = (EditText) findViewById(R.id.descriptionEditText);

        costEditText.setText(entity.getCost().toPlainString());
        dateRegEditText.setText(Util.dateToString(entity.getDateReg()));
        descriptionEditText.setText(entity.getDescription());

        costEditText.requestFocus();
        costEditText.selectAll();
        costEditText.setOnEditorActionListener(new OnEditorActionTabBehavior(dateRegEditText, doneListener));
        dateRegEditText.setOnEditorActionListener(new OnEditorActionTabBehavior(descriptionEditText, doneListener));
        descriptionEditText.setOnEditorActionListener(new OnEditorActionTabBehavior(null, doneListener));
    }
}
