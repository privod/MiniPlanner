package ru.home.miniplanner.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by privod on 19.10.2015.
 */
@DatabaseTable
public class Party extends Domain {
    public static final String EXTRA_NAME = "party";

    @DatabaseField
    private String name;
//    @DatabaseField(dataType = DataType.BIG_DECIMAL)
//    private BigDecimal deposit;                         // ?????
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Plan plan;
    @ForeignCollectionField
    private Collection<Bay> bays;
    @ForeignCollectionField(foreignFieldName = "to")
    private Collection<Contribution> in;
    @ForeignCollectionField(foreignFieldName = "from")
    private Collection<Contribution> out;

    public BigDecimal getBaysTotalCost() {
        BigDecimal totalCost = new BigDecimal("0");
        for (Bay bay : bays) {
            totalCost = totalCost.add(bay.getCost());
        }
        return totalCost;
    }

    public BigDecimal getDebt() {
//        BigDecimal summaryIn = new BigDecimal("0");
//        for (Contribution contribution: in) {
//            summaryIn.add(contribution.getSum());
//        }
//        BigDecimal summaryOut = new BigDecimal("0");
//        for (Contribution contribution: out) {
//            summaryOut.add(contribution.getSum());
//        }
        return plan.getShare().subtract(this.getBaysTotalCost());

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public BigDecimal getDeposit() {
//        return deposit;
//    }
//
//    public void setDeposit(BigDecimal deposit) {
//        this.deposit = deposit;
//    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

//    public List<Bay> getBays() {
//        if (bays instanceof List) {
//            return (List<Bay>)bays;
//        } else {
//            return new ArrayList<Bay>(bays);
//        }
//    }

//    public void setBays(List<Bay> bays) {
//        this.bays = bays;
//    }

    public Collection<Bay> getBays() {
        return bays;
    }

    public void setBays(Collection<Bay> bays) {
        this.bays = bays;
    }

    public Collection<Contribution> getIn() {
        return in;
    }

    public void setIn(Collection<Contribution> in) {
        this.in = in;
    }

    public Collection<Contribution> getOut() {
        return out;
    }

    public void setOut(Collection<Contribution> out) {
        this.out = out;
    }
}
