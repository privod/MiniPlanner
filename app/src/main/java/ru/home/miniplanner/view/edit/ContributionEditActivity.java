package ru.home.miniplanner.view.edit;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.math.BigDecimal;
import java.util.List;

import ru.home.miniplanner.R;
import ru.home.miniplanner.Util;
import ru.home.miniplanner.db.HelperFactory;
import ru.home.miniplanner.db.PartyDao;
import ru.home.miniplanner.model.Contribution;
import ru.home.miniplanner.model.Party;

public class ContributionEditActivity extends EditActivity<Contribution> {

    private EditText sumEditText;
    private EditText dateRegEditText;
//    private EditText descriptionEditText;
    private Spinner toSpinner;

    Contribution contribution;

//    public ContributionEditActivity() {
//        super(R.layout.activity_contribution_edit);
//    }

    @Override
    public void changeEntity() {
        entity.setSum(new BigDecimal(sumEditText.getText().toString()));
        entity.setDateReg(Util.dateParse(dateRegEditText.getText().toString()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dao = HelperFactory.getHelper().getContributionDao();

        LinearLayout layout = (LinearLayout) findViewById(R.id.edit_content);
        getLayoutInflater().inflate(R.layout.edit_plan, layout, true);          // TODO добавить R.layout.edit_contribution

        sumEditText = (EditText) findViewById(R.id.sumEditText);
        dateRegEditText = (EditText) findViewById(R.id.dateRegEditText);
//        descriptionEditText = (EditText) findViewById(R.id.descriptionEditText);
        toSpinner = (Spinner) findViewById(R.id.toSpinner);

        sumEditText.setText(contribution.getSum().toPlainString());
        dateRegEditText.setText(Util.dateToString(contribution.getDateReg()));
//        descriptionEditText.setText(contribution.getDescription());

        Long planId = contribution.getFrom().getPlan().getId();
        PartyDao partyDao = HelperFactory.getHelper().getPartyDao();
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
