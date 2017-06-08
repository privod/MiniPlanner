package ru.bespalov.miniplanner;

import android.util.Log;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import ru.bespalov.miniplanner.model.Bay;
import ru.bespalov.miniplanner.model.Contribution;
import ru.bespalov.miniplanner.model.Domain;
import ru.bespalov.miniplanner.model.Party;
import ru.bespalov.miniplanner.model.Plan;

/**
 * Created by privod on 24.08.2016.
 */
public class Util {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", new Locale("ru"));

    public static String getLogTag(Objects objects) {
        return objects.getClass().getSimpleName();
    }

    public static String dateToString(Date date) {
        return dateFormat.format(date);
    }

    public static Date dateParse(String date) {
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            Log.e(Util.class.getSimpleName(), e.getMessage());
            return null;
        }
    }

    public static <T extends Domain> int positionById(List<T> list, long id) {
        for (int position = 0; position < list.size(); position++) {
            if (list.get(position).getId() == id) {
                return position;
            }
        }

        return -1;
    }

    public static Plan getPlanInstance(String name, Date reg) {
        Plan plan = new Plan();
        plan.setName(name);
        plan.setDateReg(reg);
        return plan;
    }

    public static Party getPartyInstance(String name, Plan plan) {
        Party party = new Party();
        party.setPlan(plan);
        party.setName(name);
        return party;
    }

    public static Contribution getContributionInstance(Party partyFrom, Party partyTo, BigDecimal sum) {
        Contribution contribution = new Contribution();
        contribution.setFrom(partyFrom);
        contribution.setTo(partyTo);
        contribution.setSum(sum);
        return contribution;
    }

    public static Bay getBayInstace(String description, BigDecimal sum, Date reg, Party party) {
        Bay bay = new Bay();
        bay.setDescription(description);
        bay.setSum(sum);
        bay.setDateReg(reg);
        bay.setParty(party);
        return bay;
    }
}
