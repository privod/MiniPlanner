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
import ru.home.miniplanner.model.Bay;
import ru.home.miniplanner.model.Contribution;
import ru.home.miniplanner.view.adapter.BaseAdapter;
import ru.home.miniplanner.view.adapter.BayAdapter;
import ru.home.miniplanner.view.adapter.ContributionAdapter;
import ru.home.miniplanner.view.edit.BayEditActivity;

/**
 * Created by bespalov on 17.05.17.
 */

public class BayFragment extends PartyContentFragment<Bay> {

    public BayFragment() {
        super(BayEditActivity.class, Bay.class, null);
    }

    @Override
    protected Bay newEntityInstance() {
        Bay bay = new Bay();
        bay.setParty(activity.party);
        return bay;
    }

//    @Override
//    protected Dao getDaoInstance() {
//        return HelperFactory.getHelper().getBayDao();
//    }
//
//    @Override
//    protected BaseAdapter getAdapterInstance() {
//        return new BayAdapter(multiSelector);
//    }

    @Override
    protected List<Bay> getList() {
        partyDao.refresh(activity.party);
        return new ArrayList<>(activity.party.getBays());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dao = HelperFactory.getHelper().getBayDao();
        adapter = new BayAdapter(multiSelector);
    }
}
