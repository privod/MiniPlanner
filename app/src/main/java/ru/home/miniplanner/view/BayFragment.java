package ru.home.miniplanner.view;

import android.os.Bundle;

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dao = HelperFactory.getHelper().getBayDao();
        adapter = new BayAdapter(multiSelector);

    }
}
