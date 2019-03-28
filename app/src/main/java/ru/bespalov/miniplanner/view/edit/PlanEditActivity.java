package ru.bespalov.miniplanner.view.edit;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.widget.EditText;

import java.util.Locale;

import ru.bespalov.miniplanner.R;
import ru.bespalov.miniplanner.Util;
import ru.bespalov.miniplanner.db.HelperFactory;
import ru.bespalov.miniplanner.model.Plan;

public class PlanEditActivity extends EditActivity<Plan> {

    private EditText nameEditText;
    private EditText dateRegEditText;
    private EditText scaleEditText;

    public PlanEditActivity() {
        super(Plan.class);
    }

    @Override
    public void changeEntity() {
        entity.setName(nameEditText.getText().toString());
        entity.setDateReg(Util.dateParse(dateRegEditText.getText().toString()));
        entity.setScale(Integer.parseInt(scaleEditText.getText().toString()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dao = HelperFactory.getHelper().getPlanDao();

        getLayoutInflater().inflate(R.layout.edit_plan, layout, true);

        nameEditText = (EditText) findViewById(R.id.edit_text_name);
        dateRegEditText = (EditText) findViewById(R.id.edit_text_date);
        scaleEditText = (EditText) findViewById(R.id.edit_text_scale);

        TextInputLayout nameInputLayout = (TextInputLayout) findViewById(R.id.input_layout_name);
        TextInputLayout dateRegInputLayout = (TextInputLayout) findViewById(R.id.input_layout_date);
        TextInputLayout scaleInputLayout = (TextInputLayout) findViewById(R.id.input_layout_scale);

        nameEditText.setText(entity.getName());
        dateRegEditText.setText(Util.dateToString(entity.getDateReg()));
        scaleEditText.setText(String.format(Locale.getDefault(), "%s", entity.getScale()));

        setFocus(nameEditText);
        editTextSetListeners(nameEditText, dateRegEditText, nameInputLayout);
        editTextSetListeners(dateRegEditText, scaleEditText, dateRegInputLayout);
        editTextSetListeners(scaleEditText, null, scaleInputLayout);
    }
}
