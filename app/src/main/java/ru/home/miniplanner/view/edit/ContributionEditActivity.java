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
import ru.home.miniplanner.view.edit.editoraction.NextEditorAction;
import ru.home.miniplanner.view.edit.editoraction.OnEditorActionListener;

public class ContributionEditActivity extends EditActivity<Contribution> {

    ArrayAdapter<Party> adapter;

    private EditText sumEditText;
    private EditText dateRegEditText;
    private Spinner toSpinner;

    public ContributionEditActivity() {
        super(Contribution.class);
    }

//    public ContributionEditActivity() {
//        super(R.layout.activity_contribution_edit);
//    }

    @Override
    public void changeEntity() {
        entity.setSum(new BigDecimal(sumEditText.getText().toString()));
        entity.setDateReg(Util.dateParse(dateRegEditText.getText().toString()));
        entity.setTo(adapter.getItem(toSpinner.getSelectedItemPosition()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dao = HelperFactory.getHelper().getContributionDao();

        LinearLayout layout = (LinearLayout) findViewById(R.id.edit_content);
        getLayoutInflater().inflate(R.layout.edit_contribution, layout, true);

        sumEditText = (EditText) findViewById(R.id.edit_text_sum);
        dateRegEditText = (EditText) findViewById(R.id.edit_text_date);
        toSpinner = (Spinner) findViewById(R.id.spinner_to);

        sumEditText.setText(entity.getSum().toPlainString());
        dateRegEditText.setText(Util.dateToString(entity.getDateReg()));

        Long planId = entity.getFrom().getPlan().getId();
        PartyDao partyDao = HelperFactory.getHelper().getPartyDao();
        List<Party> partyList = partyDao.getOtherParty(planId, entity.getFrom().getId());
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, partyList);
        toSpinner.setAdapter(adapter);

        sumEditText.requestFocus();
        sumEditText.selectAll();
//        sumEditText.setOnEditorActionListener(new OnEditorActionTabBehavior(dateRegEditText, doneAction));
//        dateRegEditText.setOnEditorActionListener(new OnEditorActionTabBehavior(null, doneAction));
        sumEditText.setOnEditorActionListener(new OnEditorActionListener(new NextEditorAction(dateRegEditText), doneAction, goAction));
        dateRegEditText.setOnEditorActionListener(new OnEditorActionListener(null, doneAction, goAction));
    }
}
