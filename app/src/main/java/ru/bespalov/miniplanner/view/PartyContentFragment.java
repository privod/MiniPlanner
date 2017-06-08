package ru.bespalov.miniplanner.view;

import android.app.Activity;
import android.os.Bundle;

import ru.bespalov.miniplanner.db.HelperFactory;
import ru.bespalov.miniplanner.db.PartyDao;
import ru.bespalov.miniplanner.model.Domain;

/**
 * Created by bespalov on 17.05.17.
 */

public abstract class PartyContentFragment <T extends Domain> extends BaseListFragment<T> {

    PartyDao partyDao;

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
    }
}
