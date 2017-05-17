package ru.home.miniplanner.view;

import android.os.Bundle;

import ru.home.miniplanner.db.HelperFactory;
import ru.home.miniplanner.model.Contribution;
import ru.home.miniplanner.view.BaseListFragment;
import ru.home.miniplanner.view.adapter.ContributionAdapter;
import ru.home.miniplanner.view.adapter.PlanAdapter;

/**
 * Created by bespalov on 17.05.17.
 */

public class ContributionsFragment extends BaseListFragment<Contribution> {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dao = HelperFactory.getHelper().getContributionDao();
        adapter = new ContributionAdapter(multiSelector);

    }
}
