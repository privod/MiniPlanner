package ru.home.miniplanner.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ru.home.miniplanner.db.HelperFactory;
import ru.home.miniplanner.model.Contribution;
import ru.home.miniplanner.view.BaseListFragment;
import ru.home.miniplanner.view.adapter.ContributionAdapter;
import ru.home.miniplanner.view.adapter.PlanAdapter;

/**
 * Created by bespalov on 17.05.17.
 */

public class ContributionsFragment extends PartyContentFragment<Contribution> {

    @Override
    protected Contribution newEntityInstance() {
        Contribution contribution = new Contribution();
        contribution.setFrom(party);
        return contribution;
    }

    @Override
    protected List<Contribution> getList() {
        partyDao.refresh(party);
        return new ArrayList<>(party.getOut());
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
