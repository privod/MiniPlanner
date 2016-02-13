package ru.home.miniplanner.view.edit;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import ru.home.miniplanner.R;
import ru.home.miniplanner.model.Bay;
import ru.home.miniplanner.model.Plan;

/**
 * Created by bespalov on 08.12.15.
 */
public class BayEditActivity extends EditActivity<Bay> {
    static final String LOG_TAG = BayEditActivity.class.getSimpleName();

    private EditText costEditText;
    private EditText dateRegEditText;
    private EditText descriptionEditText;
    Bay bay;

    public BayEditActivity() {
        super(R.layout.activity_bay_edit);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = this.getIntent();
        bay = (Bay) intent.getSerializableExtra(Bay.EXTRA_NAME);


        doneListener = new OnEditorActionDoneListener() {
            @Override
            public void onActionDone() {
                bay.setCost(getViewService().textViewGetDecimal(costEditText));
                bay.setDateReg(getViewService().textViewGetDate(dateRegEditText));
                bay.setDescription(getViewService().textViewGetString(descriptionEditText));

                Intent intent = new Intent();
                intent.putExtra(Bay.EXTRA_NAME, bay);
                setResult(RESULT_OK, intent);
                finish();
            }
        };

        costEditText = (EditText) findViewById(R.id.costEditText);
        dateRegEditText = (EditText) findViewById(R.id.dateRegEditText);
        descriptionEditText = (EditText) findViewById(R.id.descriptionEditText);

        getViewService().textViewSetText(costEditText, bay.getCost());
        getViewService().textViewSetText(dateRegEditText, bay.getDateReg());
        getViewService().textViewSetText(descriptionEditText, bay.getDescription());

        costEditText.requestFocus();
        costEditText.selectAll();
        costEditText.setOnEditorActionListener(new OnEditorActionTabBehavior(dateRegEditText, doneListener));
        dateRegEditText.setOnEditorActionListener(new OnEditorActionTabBehavior(descriptionEditText, doneListener));
        descriptionEditText.setOnEditorActionListener(new OnEditorActionTabBehavior(null, doneListener));
    }
}
