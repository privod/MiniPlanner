package ru.home.miniplanner.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

/**
 * Created by privod on 19.10.2015.
 */
@DatabaseTable
public class Party extends Domain {

    @DatabaseField
    private String name;
//    @DatabaseField(dataType = DataType.BIG_DECIMAL)
//    private BigDecimal deposit;                         // ?????
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Plan plan;
    @ForeignCollectionField
    private Collection<Bay> bays;
    private Collection<Contribution> in;
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

    public Collection<Bay> getBays() {
        return bays;
    }

    public void setBays(List<Bay> bays) {
        this.bays = bays;
    }

//    public Collection<Contribution> getContributions() {
//        return contributions;
//    }
//
//    public void setContributions(Collection<Contribution> contributions) {
//        this.contributions = contributions;
//    }
}
