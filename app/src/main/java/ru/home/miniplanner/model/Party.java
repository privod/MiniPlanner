package ru.home.miniplanner.model;

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

    @DatabaseField
    private String name;
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Plan plan;
    @ForeignCollectionField
    private Collection<Bay> bays;
    @ForeignCollectionField(foreignFieldName = "to")
    private Collection<Contribution> in;
    @ForeignCollectionField(foreignFieldName = "from")
    private Collection<Contribution> out;

    BigDecimal getTotalCostBays() {
        BigDecimal totalCost = new BigDecimal("0");
        for (Bay bay : bays) {
            totalCost = totalCost.add(bay.getSum());
        }
        return totalCost;
    }

    public BigDecimal getTotalSumIn() {
        return getTotalSumContributions(getIn());
    }

    private BigDecimal getTotalSumOut() {
        return getTotalSumContributions(getOut());
    }

    private BigDecimal getTotalSumContributions(Collection<Contribution> contributions) {
        BigDecimal totalSum = new BigDecimal("0");
        for (Contribution contribution : contributions) {
            totalSum = totalSum.add(contribution.getSum());
        }
        return totalSum;
    }

    public BigDecimal getBalance() {
        return getTotalCostBays()
                .add(getTotalSumOut())
                .subtract(getTotalSumIn())
                .subtract(plan.getShare());

    }

    public BigDecimal getDebt() {
        BigDecimal debt = getBalance().negate();
        if (debt.signum() > 0) {
            return debt;
        }

        return new BigDecimal("0");
    }

    public BigDecimal getOverpay() {
        if (getBalance().compareTo(new BigDecimal("0")) > 0) {
            return getBalance();
        }

        return new BigDecimal("0");
    }

    /* Looks for an optimal contributor for a contribution from the list of other participants
     */
    public Party findOptimalParty(List<Party> otherParties) {

        if (this.getDebt().signum() == 0) {
            return  null;
        }

        BigDecimal diffMin = null;
        Party optimalParty = null;
        for (Party otherParty: otherParties) {
            if (otherParty.getOverpay().signum() == 0) {
                continue;
            }

            BigDecimal diff = otherParty.getOverpay().subtract(this.getDebt()).abs();
            if (null == optimalParty || (diff.compareTo(diffMin) < 0)) {
                optimalParty = otherParty;
                diffMin = diff;
            }
        }

        return optimalParty;
    }

    @Override
    public String toString() {
        return getName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

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
