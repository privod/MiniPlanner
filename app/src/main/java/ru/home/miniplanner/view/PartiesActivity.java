package ru.home.miniplanner.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import ru.home.miniplanner.R;
import ru.home.miniplanner.db.HelperFactory;
import ru.home.miniplanner.model.Bay;
import ru.home.miniplanner.model.Party;
import ru.home.miniplanner.model.Plan;
import ru.home.miniplanner.service.BayDao;
import ru.home.miniplanner.service.PartyDao;
import ru.home.miniplanner.service.PlanDao;
import ru.home.miniplanner.view.adapter.PartyAdapter;
import ru.home.miniplanner.view.edit.BayEditActivity;
import ru.home.miniplanner.view.edit.PartyEditActivity;

public class PartiesActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private static final String LOG_TAG = PartiesActivity.class.getSimpleName();
    //    private static final int REQUEST_PARTIES = 10;
    private static final int REQUEST_PARTY_EDIT = 40;
    private static final int REQUEST_BAY_EDIT = 50;

    Plan plan;
    PlanDao planDao;
    PartyDao partyDao;
    BayDao bayDao;
    PartyAdapter partyAdapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parties);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        planDao = HelperFactory.getHelper().getPlanDao();
        partyDao = HelperFactory.getHelper().getPartyDao();
        bayDao = HelperFactory.getHelper().getBayDao();
        Intent intent = getIntent();
//        plan = (Plan) intent.getSerializableExtra("plan");
        plan = (Plan) intent.getSerializableExtra("plan");
        planDao.refresh(plan);
//        partyAdapter = new PartyAdapter(this, partyDao.getByPlanId(plan.getId()));
        List<Party> parties = plan.getParties();
        for (Party party : parties) {
            partyDao.refresh(party);
        }
        partyAdapter = new PartyAdapter(this, parties);
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(partyAdapter);

        registerForContextMenu(listView);
        listView.setOnItemClickListener(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Party party = new Party();
                party.setPlan(plan);
                openPartyEditActivity(party);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (null == data) {
            return;
        }

        if (requestCode == REQUEST_PARTY_EDIT && resultCode == RESULT_OK) {
            Party party = (Party) data.getSerializableExtra("party");
            partyDao.save(party);
            planDao.refresh(plan);
            partyAdapter.setData(plan.getParties());
//            partyAdapter.setData(partyDao.getByPlanId(plan.getId()));
            partyAdapter.notifyDataSetChanged();
        }
        else if (requestCode == REQUEST_BAY_EDIT && resultCode == RESULT_OK) {
//            Party party = (Party) data.getSerializableExtra("party");
            Bay bay = (Bay) data.getSerializableExtra("bay");
            bayDao.save(bay);
            partyDao.refresh(bay.getParty());
            partyAdapter.setData(plan.getParties());
//            partyAdapter.setData(partyDao.getByPlanId(plan.getId()));
            partyAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Party party = (Party) listView.getItemAtPosition(position);
        openPartyEditActivity(party);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_party, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        long id = item.getItemId();
        Party party = (Party) listView.getItemAtPosition(menuInfo.position);

        if (id == R.id.context_party_edit) {
            openPartyEditActivity(party);
            return true;
        } else if (id == R.id.context_party_del) {
            partyDelete(party);
            return true;
        }

        return super.onContextItemSelected(item);
    }

    public void openPartyEditActivity(Party party) {
        Intent intent = new Intent(PartiesActivity.this, PartyEditActivity.class);
        intent.putExtra("party", party);
        startActivityForResult(intent, REQUEST_PARTY_EDIT);
    }

    public void partyDelete (Party party) {
        partyDao.delete(party);
        planDao.refresh(plan);
        partyAdapter.setData(plan.getParties());
        partyAdapter.notifyDataSetChanged();
    }

    public void openBayEditActivity(Bay bay) {
        Intent intent = new Intent(PartiesActivity.this, BayEditActivity.class);
        intent.putExtra("bay", bay);
        startActivityForResult(intent, REQUEST_BAY_EDIT);
    }
}
