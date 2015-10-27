package ru.home.miniplanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import ru.home.miniplanner.model.Domain;
import ru.home.miniplanner.model.Plan;
import ru.home.miniplanner.view.ViewService;

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
