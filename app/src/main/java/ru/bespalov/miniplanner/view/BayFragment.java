package ru.bespalov.miniplanner.view;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import ru.bespalov.miniplanner.db.HelperFactory;
import ru.bespalov.miniplanner.model.Bay;
import ru.bespalov.miniplanner.view.adapter.BayAdapter;
import ru.bespalov.miniplanner.view.edit.BayEditActivity;

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
