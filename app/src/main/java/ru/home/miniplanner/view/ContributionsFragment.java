package ru.home.miniplanner.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ru.home.miniplanner.db.Dao;
import ru.home.miniplanner.db.HelperFactory;
import ru.home.miniplanner.model.Contribution;
import ru.home.miniplanner.view.BaseListFragment;
import ru.home.miniplanner.view.adapter.BaseAdapter;
import ru.home.miniplanner.view.adapter.ContributionAdapter;
import ru.home.miniplanner.view.adapter.PlanAdapter;
import ru.home.miniplanner.view.edit.ContributionEditActivity;

/**
 * Created by bespalov on 17.05.17.
 */

public class ContributionsFragment extends PartyContentFragment<Contribution> {

    public ContributionsFragment() {
        super(ContributionEditActivity.class, Contribution.class, null);
    }

    @Override
    protected Contribution newEntityInstance() {
        Contribution contribution = new Contribution();
        contribution.setFrom(activity.party);
        return contribution;
    }

//    @Override
//    protected Dao getDaoInstance() {
//        return HelperFactory.getHelper().getContributionDao();
//    }
//
//    @Override
//    protected BaseAdapter getAdapterInstance() {
//        return new ContributionAdapter(multiSelector);
//    }

    @Override
    protected List<Contribution> getList() {
        partyDao.refresh(activity.party);
        return new ArrayList<>(activity.party.getOut());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dao = HelperFactory.getHelper().getContributionDao();
        adapter = new ContributionAdapter(multiSelector);
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = super.onCreateView(inflater, container, savedInstanceState);
//
//        recyclerView.setAdapter(adapter);
//
//        return view;
//    }
//    @Override
//    public void onResume() {
//        super.onResume();
//
//        partyDao.refresh(party);
//    }
}
