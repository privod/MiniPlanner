package ru.home.miniplanner;

import java.util.Date;
import java.util.GregorianCalendar;

import ru.home.miniplanner.model.Party;
import ru.home.miniplanner.model.Plan;

/**
 * Created by Леонид on 21.05.2017.
 */

public class Utils {

    public static Plan getPlan(String name, Date reg) {
        Plan plan = new Plan();
        plan.setName(name);
        plan.setDateReg(reg);
        return plan;
    }

    public static Party getParty(String name, Plan plan) {
        Party party = new Party();
        party.setPlan(plan);
        party.setName(name);
        return party;
    }


}
