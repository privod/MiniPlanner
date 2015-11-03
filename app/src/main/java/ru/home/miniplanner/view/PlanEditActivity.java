package ru.home.miniplanner.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import ru.home.miniplanner.R;
import ru.home.miniplanner.model.Plan;

public class PlanEditActivity extends EditActivity<Plan> {
    static final String LOG_TAG = PlanEditActivity.class.getSimpleName();

    private EditText nameEditText;
    private EditText dateRegEditText;
    private EditText costExpectEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewService.textViewSetText(nameEditText, entity.getName());
        viewService.textViewSetText(dateRegEditText, entity.getDateReg());
        viewService.textViewSetText(costExpectEditText, entity.getCostExpect());
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_plan_edit;
    }

    @Override
    protected Plan getEntity() {
        Intent intent = getIntent();
        return (Plan) intent.getSerializableExtra("plan");
    }

    @Override
    protected EditText[] getArrayEdits() {
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        dateRegEditText = (EditText) findViewById(R.id.dateRegEditText);
        costExpectEditText = (EditText) findViewById(R.id.costExpectEditText);

        return new EditText[] {nameEditText, dateRegEditText, costExpectEditText};
    }

    @Override
    protected void editResultOk() {
        entity.setName(viewService.textViewGetString(nameEditText));
        entity.setDateReg(viewService.textViewGetDate(dateRegEditText));
        entity.setCostExpect(viewService.textViewGetDecimal(costExpectEditText));

        Intent intent = new Intent();
        intent.putExtra("plan", entity);
        setResult(RESULT_OK, intent);
        finish();
    }
}
