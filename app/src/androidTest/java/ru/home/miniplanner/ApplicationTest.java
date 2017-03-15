package ru.home.miniplanner;

import android.app.Application;
import android.test.ApplicationTestCase;

import java.math.BigDecimal;
import java.util.ArrayList;

import ru.home.miniplanner.model.Bay;
import ru.home.miniplanner.model.Contribution;
import ru.home.miniplanner.model.Party;
import ru.home.miniplanner.model.Plan;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);

        Plan plan = new Plan();
        plan.setParties(new ArrayList<Party>());

        Party party = new Party();
        party.setPlan(plan);
        party.setBays(new ArrayList<Bay>());
        plan.getParties().add(party);

        Bay bay = new Bay();
        bay.setParty(party);
        bay.setSum(new BigDecimal("123"));
        party.getBays().add(bay);

        bay = new Bay();
        bay.setParty(party);
        bay.setSum(new BigDecimal("321"));
        party.getBays().add(bay);

        plan.getTotalCost();

        assertEquals(plan.getTotalCost(), new BigDecimal("144"));
        assertEquals(plan.getShare(), new BigDecimal("244"));
    }
}