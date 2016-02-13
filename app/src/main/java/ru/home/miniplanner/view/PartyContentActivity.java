package ru.home.miniplanner.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TabHost;

import ru.home.miniplanner.R;
import ru.home.miniplanner.db.HelperFactory;
import ru.home.miniplanner.model.Bay;
import ru.home.miniplanner.model.Party;
import ru.home.miniplanner.service.BayDao;
import ru.home.miniplanner.service.PartyDao;
import ru.home.miniplanner.view.adapter.BayAdapter;
import ru.home.miniplanner.view.edit.BayEditActivity;
import ru.home.miniplanner.view.edit.PartyEditActivity;

public class PartyContentActivity extends AppCompatActivity {
    private static final int REQUEST_BAY_EDIT = 50;

    private ListView bayListView;
    private ListView contributionListView;

    PartyDao partyDao;
    BayDao bayDao;
    BayAdapter bayAdapter;

    Party party;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party_content);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        partyDao = HelperFactory.getHelper().getPartyDao();

        Intent intent = this.getIntent();
        party = (Party) intent.getSerializableExtra(Party.EXTRA_NAME);

        TabHost tabs = (TabHost) findViewById(android.R.id.tabhost);
        tabs.setup();
        TabHost.TabSpec spec = tabs.newTabSpec("bayTag");
        spec.setContent(R.id.bayTab);
        spec.setIndicator("Bay list");
        tabs.addTab(spec);
        spec = tabs.newTabSpec("contributionTag");
        spec.setContent(R.id.contributionTab);
        spec.setIndicator("Contribution list");
        tabs.addTab(spec);
        tabs.setCurrentTab(0);

        bayListView = (ListView) findViewById(R.id.bayListView);
        bayAdapter = new BayAdapter(this);
        bayListView.setAdapter(bayAdapter);

        contributionListView = (ListView) findViewById(R.id.contributionListView);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        partyDao.refresh(party);
        bayAdapter.setList(party.getBays());
        bayAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (null == data) {
            return;
        }

        if (requestCode == REQUEST_BAY_EDIT && resultCode == RESULT_OK) {
            Bay bay = (Bay) data.getSerializableExtra(Bay.EXTRA_NAME);
            party.getBays().add(bay);
//            bayDao.save(bay);
//            partyDao.refresh(bay.getParty());
            bayAdapter.setList(party.getBays());
            bayAdapter.notifyDataSetChanged();
        }
    }

    public void openBayEditActivity(Bay bay) {
        Intent intent = new Intent(PartyContentActivity.this, BayEditActivity.class);
        intent.putExtra(Bay.EXTRA_NAME, bay);
        startActivityForResult(intent, REQUEST_BAY_EDIT);
    }
}
