package ru.bespalov.miniplanner.view;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.bespalov.miniplanner.R;
import ru.bespalov.miniplanner.db.Dao;
import ru.bespalov.miniplanner.db.HelperFactory;
import ru.bespalov.miniplanner.model.Party;
import ru.bespalov.miniplanner.model.Plan;
import ru.bespalov.miniplanner.view.adapter.PartyAdapter;
import ru.bespalov.miniplanner.view.edit.PartyEditActivity;

public class PartiesActivity extends BaseListActivity<Party> {

    Dao<Plan> planDao;

    Plan plan;
//    PartyAdapter partyAdapter;      // TODO Переименовать в adapter

    TextView totalCostTextView;
    TextView partiesCountTextView;
    TextView shareTextView;

    public PartiesActivity() {
        super(PartyEditActivity.class, Party.class, PartyContentActivity.class);

        request_code_edit = RequestCode.PARTY_EDIT;
    }

    @Override
    protected Party newEntityInstance() {
        Party party = new Party();
        party.setPlan(plan);
        return party;
    }

//    @Override
//    protected Dao<Party> getDaoInstance() {
//        return HelperFactory.getHelper().getPartyDao();
//    }
//
//    @Override
//    protected BaseAdapter<? extends BaseAdapter.ViewHolder, Party> getAdapterInstance() {
//        return new PartyAdapter(multiSelector);
//    }

    @Override
    protected List<Party> getList() {
        planDao.refresh(plan);
        return new ArrayList<>(plan.getParties());
    }

    @Override
    protected void onCreateBeforeView() {
        planDao = HelperFactory.getHelper().getPlanDao();
        plan = (Plan) getIntent().getSerializableExtra(Plan.class.getSimpleName());

        dao = HelperFactory.getHelper().getPartyDao();
        adapter = new PartyAdapter(multiSelector);

        setTitle(plan.getName());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        totalCostTextView = (TextView) findViewById(R.id.text_vew_total_cost);
        partiesCountTextView = (TextView) findViewById(R.id.text_vew_parties_count);
        shareTextView = (TextView) findViewById(R.id.text_vew_share);
    }

    @Override
    protected void onResume() {
        super.onResume();

        refreshSubtitle();
    }

    @Override
    protected void layoutInflateSubtitle(CoordinatorLayout layout) {
        getLayoutInflater().inflate(R.layout.app_bar_toolbar_parties, layout, true);
    }

    @Override
    public void refreshSubtitle() {
        totalCostTextView.setText(plan.getTotalCost().toPlainString());
        partiesCountTextView.setText(String.valueOf(plan.getParties().size()));
        shareTextView.setText(plan.getShare().toPlainString());
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        recyclerView.setAdapter(adapter);
//    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        planDao.refresh(plan);
//        adapter.updateData(new ArrayList<>(plan.getParties()));
//    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//
////        partyAdapter.updateParties(new ArrayList<>(plan.getParties()));
//        partyAdapter.setParentList(new ArrayList<>(plan.getParties()), true);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        if (null == data) {
//            return;
//        }
//
//        if (requestCode == RequestCode.PARTY_EDIT && resultCode == RESULT_OK) {
//            Party party = (Party) data.getSerializableExtra(Party.class.getSimpleName());
//            dao.save(party);
//            planDao.refresh(plan);
//            partyAdapter.setParentList(new ArrayList<>(plan.getParties()), true);
//        }
////        else if (requestCode == REQUEST_BAY_EDIT && resultCode == RESULT_OK) {
//////            Party party = (Party) data.getSerializableExtra("party");
////            Bay bay = (Bay) data.getSerializableExtra("bay");
////            bayDao.save(bay);
////            partyDao.refresh(bay.getParty());
////            partyAdapter.setList(plan.getParties());
//////            partyAdapter.setList(partyDao.getOtherParty(plan.getId()));
////            partyAdapter.notifyDataSetChanged();
////        }
//    }

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

//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//    }
}
