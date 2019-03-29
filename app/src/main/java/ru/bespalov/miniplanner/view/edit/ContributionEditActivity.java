package ru.bespalov.miniplanner.view.edit;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import java.math.BigDecimal;
import java.util.List;

import fr.ganfra.materialspinner.MaterialSpinner;
import ru.bespalov.miniplanner.R;
import ru.bespalov.miniplanner.Util;
import ru.bespalov.miniplanner.db.HelperFactory;
import ru.bespalov.miniplanner.db.PartyDao;
import ru.bespalov.miniplanner.model.Contribution;
import ru.bespalov.miniplanner.model.Party;

public class ContributionEditActivity extends EditActivity<Contribution> {

    ArrayAdapter<Party> adapter;

    private EditText sumEditText;
    private EditText dateRegEditText;
    private MaterialSpinner toSpinner;

    public ContributionEditActivity() {
        super(Contribution.class);
    }

    @Override
    public void changeEntity() {
        entity.setSum(new BigDecimal(sumEditText.getText().toString()));
        entity.setDateReg(Util.dateParse(dateRegEditText.getText().toString()));
        entity.setTo(adapter.getItem(toSpinner.getSelectedItemPosition() - 1));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dao = HelperFactory.getHelper().getContributionDao();

        getLayoutInflater().inflate(R.layout.edit_contribution, layout, true);

        sumEditText = (EditText) findViewById(R.id.edit_text_sum);
        dateRegEditText = (EditText) findViewById(R.id.edit_text_date);
        toSpinner = (MaterialSpinner) findViewById(R.id.spinner_to);

        TextInputLayout sumInputLayout = (TextInputLayout) findViewById(R.id.input_layout_sum);
        TextInputLayout dateRegInputLayout = (TextInputLayout) findViewById(R.id.input_layout_date);

        sumEditText.setText(entity.getSumView());
        dateRegEditText.setText(Util.dateToString(entity.getDateReg()));

        Long planId = entity.getFrom().getPlan().getId();
        PartyDao partyDao = HelperFactory.getHelper().getPartyDao();
        List<Party> partyList = partyDao.getOtherParty(planId, entity.getFrom().getId());
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, partyList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        toSpinner.setAdapter(adapter);
        toSpinner.setSelection(partyList.indexOf(entity.getTo()) + 1);

        setFocus(sumEditText);
        editTextSetListeners(sumEditText, dateRegEditText, sumInputLayout);
        editTextSetListeners(dateRegEditText, null, dateRegInputLayout);
    }
}
