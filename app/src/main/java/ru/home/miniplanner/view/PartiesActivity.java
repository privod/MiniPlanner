package ru.home.miniplanner.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collection;

import ru.home.miniplanner.R;
import ru.home.miniplanner.db.Dao;
import ru.home.miniplanner.db.HelperFactory;
import ru.home.miniplanner.model.Bay;
import ru.home.miniplanner.model.Party;
import ru.home.miniplanner.model.Plan;
import ru.home.miniplanner.service.BayDao;
import ru.home.miniplanner.db.PartyDao;
import ru.home.miniplanner.view.adapter.PartyAdapter;
import ru.home.miniplanner.view.divider.DividerItemDecoration;
import ru.home.miniplanner.view.edit.PartyEditActivity;

//public class PartiesActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
public class PartiesActivity extends BaseActivity<Party> implements AdapterView.OnItemClickListener {

    Dao<Plan> planDao;
//    ru.home.miniplanner.db.PartyDao partyDao;
//    BayDao bayDao;

    Plan plan;
    PartyAdapter partyAdapter;      // TODO Переименовать в adapter
//    protected RecyclerView recyclerView;

    public PartiesActivity() {
        super(PartyEditActivity.class);

        request_code_edit = RequestCode.PARTY_EDIT;
    }

    @Override
    protected Party newEntityInstance() {
        Party party = new Party();
        party.setPlan(plan);
        return party;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        planDao = HelperFactory.getHelper().getPlanDao();
        dao = HelperFactory.getHelper().getPartyDao();
//        bayDao = HelperFactory.getHelper().getBayDao();

        plan = (Plan) getIntent().getSerializableExtra(Plan.class.getSimpleName());   // TODO Возможно достаточно передавать ID плана, вместо объекта целиком
        planDao.refresh(plan);
//        List<Party> parties = plan.getParties();
//        for (Party party : parties) {
//            partyDao.refresh(party);
//        }
        partyAdapter = new PartyAdapter();

//        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
//        if (null != recyclerView) {
//            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
//            recyclerView.setLayoutManager(layoutManager);
//        }
        recyclerView.setAdapter(partyAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        if (null != fab) {
//            fab.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    startEditActivity(new );
//
//                }
//            });
//        }

//        registerForContextMenu(listView);
//        listView.setOnItemClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

//        partyAdapter.updateParties(new ArrayList<>(plan.getParties()));
        partyAdapter.setParentList(new ArrayList<>(plan.getParties()), true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (null == data) {
            return;
        }

        if (requestCode == RequestCode.PARTY_EDIT && resultCode == RESULT_OK) {
            Party party = (Party) data.getSerializableExtra(Party.class.getSimpleName());
            dao.save(party);
            planDao.refresh(plan);
            partyAdapter.setParentList(new ArrayList<>(plan.getParties()), true);
        }
//        else if (requestCode == REQUEST_BAY_EDIT && resultCode == RESULT_OK) {
////            Party party = (Party) data.getSerializableExtra("party");
//            Bay bay = (Bay) data.getSerializableExtra("bay");
//            bayDao.save(bay);
//            partyDao.refresh(bay.getParty());
//            partyAdapter.setList(plan.getParties());
////            partyAdapter.setList(partyDao.getByPlanId(plan.getId()));
//            partyAdapter.notifyDataSetChanged();
//        }
    }

//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Party party = partyAdapter.getParties().get(position);
//        openPartyContentActivity(party);
//    }

//    @Override
//    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//        super.onCreateContextMenu(menu, v, menuInfo);
//        getMenuInflater().inflate(R.menu.context_party, menu);
//    }

//    @Override
//    public boolean onContextItemSelected(MenuItem item) {
//        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
//        long id = item.getItemId();
//        Party party = (Party) listView.getItemAtPosition(menuInfo.position);
//
//        if (id == R.id.context_party_edit) {
//            openPartyEditActivity(party);
//            return true;
//        } else if (id == R.id.context_party_del) {
//            partyDelete(party);
//            return true;
//        }
//
//        return super.onContextItemSelected(item);
//    }

//    public void openPartyEditActivity(Party party) {
//        Intent intent = new Intent(PartiesActivity.this, PartyEditActivity.class);
//        intent.putExtra(Party.EXTRA_NAME, party);
//        startActivityForResult(intent, RequestCode.PARTY_EDIT);
//    }

//    public void startPartyContentActivity(int position) {
//        Party party = partyAdapter.getParentList().get(position);
//        Intent intent = new Intent(PartiesActivity.this, PartyContentActivity.class);
//        intent.putExtra(Party.EXTRA_NAME, party);
//        startActivityForResult(intent, RequestCode.PARTY_CONTENT);
//    }

//    public void partyDelete (Party party) {
//        partyDao.delete(party);
//        planDao.refresh(plan);
//        partyAdapter.set(new ArrayList<>(plan.getParties()));
//        partyAdapter.notifyDataSetChanged();
//    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
