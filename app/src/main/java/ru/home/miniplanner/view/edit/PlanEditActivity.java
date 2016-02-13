package ru.home.miniplanner.view.edit;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import ru.home.miniplanner.R;
import ru.home.miniplanner.db.HelperFactory;
import ru.home.miniplanner.model.Plan;
import ru.home.miniplanner.service.PlanDao;

public class PlanEditActivity extends EditActivity<Plan> {
    static final String LOG_TAG = PlanEditActivity.class.getSimpleName();

    private EditText nameEditText;
    private EditText dateRegEditText;

    PlanDao planDao;

    private Plan plan;

    public PlanEditActivity() {
        super(R.layout.activity_plan_edit);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        planDao = HelperFactory.getHelper().getPlanDao();

        Intent intent = this.getIntent();
        plan = (Plan) intent.getSerializableExtra(Plan.EXTRA_NAME);
//        planDao.refresh(plan);

        doneListener = new OnEditorActionDoneListener() {
            @Override
            public void onActionDone() {
                plan.setName(getViewService().textViewGetString(nameEditText));
                plan.setDateReg(getViewService().textViewGetDate(dateRegEditText));
//                entity.setCostExpect(viewService.textViewGetDecimal(costExpectEditText));

                Intent intent = new Intent();
                intent.putExtra(Plan.EXTRA_NAME, plan);
                setResult(RESULT_OK, intent);
                finish();            }
        };

        nameEditText = (EditText) findViewById(R.id.nameEditText);
        dateRegEditText = (EditText) findViewById(R.id.dateRegEditText);

        getViewService().textViewSetText(nameEditText, plan.getName());
        getViewService().textViewSetText(dateRegEditText, plan.getDateReg());

        nameEditText.requestFocus();
        nameEditText.selectAll();
        nameEditText.setOnEditorActionListener(new OnEditorActionTabBehavior(dateRegEditText, doneListener));
        dateRegEditText.setOnEditorActionListener(new OnEditorActionTabBehavior(null, doneListener));
    }
}
