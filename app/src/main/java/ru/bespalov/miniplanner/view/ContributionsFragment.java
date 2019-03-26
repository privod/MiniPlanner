package ru.bespalov.miniplanner.view;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import ru.bespalov.miniplanner.db.HelperFactory;
import ru.bespalov.miniplanner.model.Contribution;
import ru.bespalov.miniplanner.model.Party;
import ru.bespalov.miniplanner.view.adapter.ContributionAdapter;
import ru.bespalov.miniplanner.view.edit.ContributionEditActivity;

/**
 * Created by bespalov on 17.05.17.
 */

public class ContributionsFragment extends PartyContentFragment<Contribution> {

    public ContributionsFragment() {
        super(ContributionEditActivity.class, null);
    }

    @Override
    protected Contribution newEntityInstance() {
        HelperFactory.getHelper().getPlanDao().refresh(activity.party.getPlan());
        List<Party> otherParties = partyDao.getOtherParty(activity.party.getPlan().getId(), activity.party.getId());
        Party optimalParty = activity.party.findOptimalParty(otherParties);

        Contribution contribution = new Contribution();
        contribution.setFrom(activity.party);
        if (null != optimalParty) {
            contribution.setTo(optimalParty);
            contribution.setSum(optimalParty.getOverpay().min(activity.party.getDebt()));
        }
        return contribution;
    }

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
}
