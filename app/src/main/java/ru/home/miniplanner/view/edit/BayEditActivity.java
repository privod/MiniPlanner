package ru.home.miniplanner.view.edit;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.math.BigDecimal;

import ru.home.miniplanner.R;
import ru.home.miniplanner.Util;
import ru.home.miniplanner.db.HelperFactory;
import ru.home.miniplanner.model.Bay;
import ru.home.miniplanner.view.edit.listeners.OnEditorActionListenerNext;
import ru.home.miniplanner.view.edit.listeners.OnEditorActionListener;

/**
 * Created by bespalov on 08.12.15.
 */
public class BayEditActivity extends EditActivity<Bay> {

    private EditText sumEditText;
    private EditText dateRegEditText;
    private EditText descriptionEditText;

    public BayEditActivity() {
        super(Bay.class);
    }

    @Override
    public void changeEntity() {
        entity.setSum(new BigDecimal(sumEditText.getText().toString()));
        entity.setDateReg(Util.dateParse(dateRegEditText.getText().toString()));
        entity.setDescription(descriptionEditText.getText().toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dao = HelperFactory.getHelper().getBayDao();

        LinearLayout layout = (LinearLayout) findViewById(R.id.edit_content);
        getLayoutInflater().inflate(R.layout.edit_bay, layout, true);          // TODO добавить R.layout.edit_bay

        sumEditText = (EditText) findViewById(R.id.edit_text_sum);
        dateRegEditText = (EditText) findViewById(R.id.edit_text_date);
        descriptionEditText = (EditText) findViewById(R.id.edit_text_description);

        sumEditText.setText(entity.getSum().toPlainString());
        dateRegEditText.setText(Util.dateToString(entity.getDateReg()));
        descriptionEditText.setText(entity.getDescription());

        sumEditText.requestFocus();
        sumEditText.selectAll();
//        sumEditText.setOnEditorActionListener(new OnEditorActionTabBehavior(dateRegEditText, doneListener));
//        dateRegEditText.setOnEditorActionListener(new OnEditorActionTabBehavior(descriptionEditText, doneListener));
//        descriptionEditText.setOnEditorActionListener(new OnEditorActionTabBehavior(null, doneListener));
        sumEditText.setOnEditorActionListener(new OnEditorActionListener(new OnEditorActionListenerNext(dateRegEditText), doneListener));
        dateRegEditText.setOnEditorActionListener(new OnEditorActionListener(new OnEditorActionListenerNext(descriptionEditText), doneListener));
        descriptionEditText.setOnEditorActionListener(new OnEditorActionListener(null, doneListener));
    }
}
