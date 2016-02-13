package ru.home.miniplanner.view.edit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;

import java.util.ArrayList;

import ru.home.miniplanner.R;
import ru.home.miniplanner.db.HelperFactory;
import ru.home.miniplanner.model.Bay;
import ru.home.miniplanner.model.Contribution;
import ru.home.miniplanner.model.Party;
import ru.home.miniplanner.model.Plan;
import ru.home.miniplanner.service.BayDao;
import ru.home.miniplanner.service.PartyDao;
import ru.home.miniplanner.view.adapter.BayAdapter;

public class PartyEditActivity extends EditActivity<Party> {
    static final String LOG_TAG = PartyEditActivity.class.getSimpleName();

    private EditText nameEditText;
    PartyDao partyDao;
    BayDao bayDao;
    BayAdapter bayAdapter;
    Party party;


    public PartyEditActivity() {
        super(R.layout.activity_party_edit);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        partyDao = HelperFactory.getHelper().getPartyDao();
        bayDao = HelperFactory.getHelper().getBayDao();

        Intent intent = this.getIntent();
        party = (Party) intent.getSerializableExtra(Party.EXTRA_NAME);

        doneListener = new OnEditorActionDoneListener() {
            @Override
            public void onActionDone() {
                party.setName(getViewService().textViewGetString(nameEditText));

                Intent intent = new Intent();
                intent.putExtra(Party.EXTRA_NAME, party);
                setResult(RESULT_OK, intent);
                finish();
            }
        };

        nameEditText = (EditText) findViewById(R.id.nameEditText);
        getViewService().textViewSetText(nameEditText, party.getName());
        nameEditText.requestFocus();
        nameEditText.selectAll();
        nameEditText.setOnEditorActionListener(new OnEditorActionTabBehavior(null, doneListener));
    }
}
