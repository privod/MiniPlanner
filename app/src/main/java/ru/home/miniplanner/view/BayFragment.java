package ru.home.miniplanner.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ru.home.miniplanner.db.HelperFactory;
import ru.home.miniplanner.model.Bay;
import ru.home.miniplanner.model.Contribution;
import ru.home.miniplanner.view.adapter.BayAdapter;
import ru.home.miniplanner.view.adapter.ContributionAdapter;

/**
 * Created by bespalov on 17.05.17.
 */

public class BayFragment extends BaseListFragment<Bay> {

    @Override
    protected Bay newEntityInstance() {
        Bay bay = new Bay();
        bay.setParty(party);
        return bay;
    }

    @Override
    protected List<Bay> getList() {
        partyDao.refresh(party);
        return new ArrayList<>(party.getBays());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dao = HelperFactory.getHelper().getBayDao();
        adapter = new BayAdapter(multiSelector);
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = super.onCreateView(inflater, container, savedInstanceState);
//
//        recyclerView.setAdapter(adapter);
//
//        return view;
//    }
}
