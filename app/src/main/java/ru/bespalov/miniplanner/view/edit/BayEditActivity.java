package ru.bespalov.miniplanner.view.edit;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.widget.EditText;

import java.math.BigDecimal;

import ru.bespalov.miniplanner.R;
import ru.bespalov.miniplanner.Util;
import ru.bespalov.miniplanner.db.HelperFactory;
import ru.bespalov.miniplanner.model.Bay;

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

        getLayoutInflater().inflate(R.layout.edit_bay, layout, true);          // TODO добавить R.layout.edit_bay

        sumEditText = (EditText) findViewById(R.id.edit_text_sum);
        dateRegEditText = (EditText) findViewById(R.id.edit_text_date);
        descriptionEditText = (EditText) findViewById(R.id.edit_text_description);

        TextInputLayout sumInputLayout = (TextInputLayout) findViewById(R.id.input_layout_sum);
        TextInputLayout dateRegInputLayout = (TextInputLayout) findViewById(R.id.input_layout_date);
        TextInputLayout descriptionRegInputLayout = (TextInputLayout) findViewById(R.id.input_layout_description);


        sumEditText.setText(entity.getSumView());
        dateRegEditText.setText(Util.dateToString(entity.getDateReg()));
        descriptionEditText.setText(entity.getDescription());

        setFocus(sumEditText);
        editTextSetListeners(sumEditText, dateRegEditText, sumInputLayout);
        editTextSetListeners(dateRegEditText, descriptionEditText, dateRegInputLayout);
        editTextSetListeners(descriptionEditText, null, descriptionRegInputLayout);
    }
}
