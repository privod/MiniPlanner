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

import ru.home.miniplanner.R;
import ru.home.miniplanner.db.HelperFactory;
import ru.home.miniplanner.model.Party;
import ru.home.miniplanner.model.Plan;
import ru.home.miniplanner.service.PartyDao;
import ru.home.miniplanner.view.adapter.PartyAdapter;
import ru.home.miniplanner.view.edit.PartyEditActivity;

public class PartiesActivity extends AppCompatActivity {
    private static final String TAG = PartiesActivity.class.getSimpleName();
    //    private static final int REQUEST_PARTIES = 10;
    private static final int REQUEST_PARTY_EDIT = 40;

    //    Plan plan;
    Plan plan;
    PartyDao partyDao;
    PartyAdapter partyAdapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parties);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        partyDao = HelperFactory.getHelper().getPartyDao();
        Intent intent = getIntent();
        plan = (Plan) intent.getSerializableExtra("plan");
        partyAdapter = new PartyAdapter(this, partyDao.getByPlanId(plan.getId()));
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(partyAdapter);

        registerForContextMenu(listView);

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

    private void openPartyEditActivity(Party party) {
        Intent intent = new Intent(PartiesActivity.this, PartyEditActivity.class);
        intent.putExtra("party", party);
        startActivityForResult(intent, REQUEST_PARTY_EDIT);
//        PartyEditDialogFragment dialogFragment = new PartyEditDialogFragment();
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("party", party);
//        dialogFragment.setArguments(bundle);
//        dialogFragment.show(getFragmentManager(), "PartyEdit");
//        Log.d(TAG, "Dialog PartyEdit is show.");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (null == data) {
            return;
        }

        if (requestCode == REQUEST_PARTY_EDIT && resultCode == RESULT_OK) {
            Party party = (Party) data.getSerializableExtra("party");
            partyDao.save(party);
            partyAdapter.setData(partyDao.getByPlanId(plan.getId()));
            partyAdapter.notifyDataSetChanged();
        }
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
            partyDao.delete(party);
            partyAdapter.setData(partyDao.getByPlanId(plan.getId()));
            partyAdapter.notifyDataSetChanged();
            return true;
        }

        return super.onContextItemSelected(item);
    }
}
