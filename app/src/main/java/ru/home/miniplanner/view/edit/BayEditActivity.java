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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        doneListener = new OnEditorActionDoneListener() {
            @Override
            public void onActionDone() {
                entity.setCost(viewService.textViewGetDecimal(costEditText));
                entity.setDateReg(viewService.textViewGetDate(dateRegEditText));
                entity.setDescription(viewService.textViewGetString(descriptionEditText));

                Intent intent = new Intent();
                intent.putExtra("bay", entity);
                setResult(RESULT_OK, intent);
                finish();
            }
        };

        costEditText = (EditText) findViewById(R.id.costEditText);
        dateRegEditText = (EditText) findViewById(R.id.dateRegEditText);
        descriptionEditText = (EditText) findViewById(R.id.descriptionEditText);

        viewService.textViewSetText(costEditText, entity.getCost());
        viewService.textViewSetText(dateRegEditText, entity.getDateReg());
        viewService.textViewSetText(descriptionEditText, entity.getDescription());

        costEditText.requestFocus();
        costEditText.selectAll();
        costEditText.setOnEditorActionListener(new OnEditorActionTabBehavior(dateRegEditText, doneListener));
        dateRegEditText.setOnEditorActionListener(new OnEditorActionTabBehavior(descriptionEditText, doneListener));
        descriptionEditText.setOnEditorActionListener(new OnEditorActionTabBehavior(null, doneListener));
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_bay_edit;
    }

    @Override
    protected Bay getEntity() {
        Intent intent = getIntent();
        return (Bay) intent.getSerializableExtra("bay");
    }
}
