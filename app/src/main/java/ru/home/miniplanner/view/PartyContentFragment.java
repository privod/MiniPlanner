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
import ru.home.miniplanner.model.Domain;
import ru.home.miniplanner.model.Party;
import ru.home.miniplanner.view.adapter.BayAdapter;

/**
 * Created by bespalov on 17.05.17.
 */

public abstract class PartyContentFragment <T extends Domain> extends BaseListFragment<T> {

    Dao<Party> partyDao;
    Party party;

    public PartyContentFragment(Class<? extends Activity> editActivityClass,
                                Class<T> entityClass,
                                Class<? extends Activity> insideActivityClass
    ) {
        super(editActivityClass, entityClass, insideActivityClass);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        partyDao = HelperFactory.getHelper().getPartyDao();
        party = (Party) activity.getIntent().getSerializableExtra(Party.class.getSimpleName());
    }
}
