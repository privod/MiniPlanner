package ru.home.miniplanner.view;

import java.util.ArrayList;
import java.util.List;

import ru.home.miniplanner.db.Dao;
import ru.home.miniplanner.db.HelperFactory;
import ru.home.miniplanner.model.Party;
import ru.home.miniplanner.model.Plan;
import ru.home.miniplanner.view.adapter.PartyAdapter;
import ru.home.miniplanner.view.edit.PartyEditActivity;

public class PartiesActivity extends BaseListActivity<Party> {

    Dao<Plan> planDao;

    Plan plan;
//    PartyAdapter partyAdapter;      // TODO Переименовать в adapter

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
//////            partyAdapter.setList(partyDao.getByPlanId(plan.getId()));
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
