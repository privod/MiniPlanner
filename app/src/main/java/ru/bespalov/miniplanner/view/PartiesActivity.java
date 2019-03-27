package ru.bespalov.miniplanner.view;

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

    TextView totalCostTextView;
    TextView partiesCountTextView;
    TextView shareTextView;

    public PartiesActivity() {
        super(PartyEditActivity.class, PartyContentActivity.class);

        request_code_edit = RequestCode.PARTY_EDIT;
    }

    @Override
    protected Party newEntityInstance() {
        Party party = new Party();
        party.setPlan(plan);
        return party;
    }

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
        partiesCountTextView.setText(String.valueOf(plan.getPartiesCount()));
        shareTextView.setText(plan.getShareCost().toPlainString());
    }
}
