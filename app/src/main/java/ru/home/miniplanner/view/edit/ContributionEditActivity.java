package ru.home.miniplanner.view.edit;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

import ru.home.miniplanner.R;
import ru.home.miniplanner.db.Dao;
import ru.home.miniplanner.db.HelperFactory;
import ru.home.miniplanner.db.PartyDao;
import ru.home.miniplanner.model.Contribution;
import ru.home.miniplanner.model.Party;

/**
 * Created by bespalov on 08.12.15.
 */
public class ContributionEditActivity extends EditActivity<Contribution> {
    static final String LOG_TAG = ContributionEditActivity.class.getSimpleName();

    private EditText sumEditText;
    private EditText dateRegEditText;
//    private EditText descriptionEditText;
    private Spinner toSpinner;

    PartyDao partyDao;

    Contribution contribution;

    public ContributionEditActivity() {
        super(R.layout.activity_contribution_edit);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = this.getIntent();
        contribution = (Contribution) intent.getSerializableExtra(Contribution.EXTRA_NAME);


        doneListener = new OnEditorActionDoneListener() {
            @Override
            public void onActionDone() {
                contribution.setSum(getViewService().textViewGetDecimal(sumEditText));
                contribution.setDateReg(getViewService().textViewGetDate(dateRegEditText));
//                Contribution.setDescription(getViewService().textViewGetString(descriptionEditText));
                Party party = (Party) toSpinner.getSelectedItem();
                partyDao.refresh(party);
                contribution.setTo(party);

                Intent intent = new Intent();
                intent.putExtra(Contribution.EXTRA_NAME, contribution);
                setResult(RESULT_OK, intent);
                finish();
            }
        };

        sumEditText = (EditText) findViewById(R.id.sumEditText);
        dateRegEditText = (EditText) findViewById(R.id.dateRegEditText);
//        descriptionEditText = (EditText) findViewById(R.id.descriptionEditText);
        toSpinner = (Spinner) findViewById(R.id.toSpinner);

        getViewService().textViewSetText(sumEditText, contribution.getSum());
        getViewService().textViewSetText(dateRegEditText, contribution.getDateReg());
//        getViewService().textViewSetText(descriptionEditText, bay.getDescription());

        Long planId = contribution.getFrom().getPlan().getId();
        partyDao = HelperFactory.getHelper().getPartyDao();
        List<Party> partyList = partyDao.getByPlanId(planId);
        ArrayAdapter<Party> adapter = new ArrayAdapter<Party>(this, android.R.layout.simple_spinner_item, partyList);
        toSpinner.setAdapter(adapter);

        sumEditText.requestFocus();
        sumEditText.selectAll();
        sumEditText.setOnEditorActionListener(new OnEditorActionTabBehavior(dateRegEditText, doneListener));
        dateRegEditText.setOnEditorActionListener(new OnEditorActionTabBehavior(null, doneListener));
//        descriptionEditText.setOnEditorActionListener(new OnEditorActionTabBehavior(null, doneListener));
    }
}
