package ru.bespalov.miniplanner.view.edit;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.widget.EditText;

import ru.bespalov.miniplanner.R;
import ru.bespalov.miniplanner.Util;
import ru.bespalov.miniplanner.db.HelperFactory;
import ru.bespalov.miniplanner.model.Plan;

public class PlanEditActivity extends EditActivity<Plan> {

    private EditText nameEditText;
    private EditText dateRegEditText;

    public PlanEditActivity() {
        super(Plan.class);
    }

    @Override
    public void changeEntity() {
        entity.setName(nameEditText.getText().toString());
        entity.setDateReg(Util.dateParse(dateRegEditText.getText().toString()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dao = HelperFactory.getHelper().getPlanDao();

//        LinearLayout layout = (LinearLayout) findViewById(R.id.edit_content);
        getLayoutInflater().inflate(R.layout.edit_plan, layout, true);

        nameEditText = (EditText) findViewById(R.id.edit_text_name);
        dateRegEditText = (EditText) findViewById(R.id.edit_text_date);

        TextInputLayout nameInputLayout = (TextInputLayout) findViewById(R.id.input_layout_name);
        TextInputLayout dateRegInputLayout = (TextInputLayout) findViewById(R.id.input_layout_date);

        nameEditText.setText(entity.getName());
        dateRegEditText.setText(Util.dateToString(entity.getDateReg()));

//        nameEditText.requestFocus();
//        nameEditText.selectAll();
        setFocus(nameEditText);
        editTextSetListeners(nameEditText, dateRegEditText, nameInputLayout);
        editTextSetListeners(dateRegEditText, null, dateRegInputLayout);
    }
}
