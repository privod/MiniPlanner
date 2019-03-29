package ru.bespalov.miniplanner.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.List;

/**
 * Created by privod on 19.10.2015.
 */
@DatabaseTable
public class Party extends Domain {

    @DatabaseField
    private String name;
    @DatabaseField(dataType = DataType.BIG_DECIMAL, defaultValue = "1")
    private BigDecimal share;
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Plan plan;
    @ForeignCollectionField
    private Collection<Bay> bays;
    @ForeignCollectionField(foreignFieldName = "to")
    private Collection<Contribution> in;
    @ForeignCollectionField(foreignFieldName = "party")
    private Collection<Contribution> out;

//    public Party() {
//        this.share = BigDecimal.ONE;
//    }

    BigDecimal getBaysCost() {
        BigDecimal totalCost = BigDecimal.ZERO;
        for (Bay bay : bays) {
            totalCost = totalCost.add(bay.getSum());
        }
        return totalCost;
    }

    public String getBaysCostView() {
        return getBaysCost().setScale(getPlan().getScale(), RoundingMode.HALF_UP).toPlainString();
    }

    private BigDecimal getTotalSumIn() {
        return getTotalSumContributions(getIn());
    }

    public String getTotalSumInView() {
        return getTotalSumIn().setScale(getPlan().getScale(), RoundingMode.HALF_UP).toPlainString();
    }

    private BigDecimal getTotalSumOut() {
        return getTotalSumContributions(getOut());
    }

    public String getTotalSumOutView() {
        return getTotalSumOut().setScale(getPlan().getScale(), RoundingMode.HALF_UP).toPlainString();
    }

    public BigDecimal getShare() {
        return share;
    }

    public String getShareView() {
        return getShare().setScale(getPlan().getScale(), RoundingMode.HALF_UP).toPlainString();
    }

    public void setShare(BigDecimal share) {
        this.share = share;
    }

    private BigDecimal getTotalSumContributions(Collection<Contribution> contributions) {
        BigDecimal totalSum = BigDecimal.ZERO;
        for (Contribution contribution : contributions) {
            totalSum = totalSum.add(contribution.getSum());
        }
        return totalSum;
    }

    public BigDecimal getBalance() {
        return getBaysCost()
                .add(getTotalSumOut())
                .subtract(getTotalSumIn())
                .subtract(plan.getShareCost());

    }

    public BigDecimal getDebt() {
        BigDecimal debt = getBalance().negate();
        if (debt.signum() > 0) {
            return debt;
        }

        return BigDecimal.ZERO;
    }

    public String getDebtView() {
        return getDebt().setScale(getPlan().getScale(), RoundingMode.HALF_UP).toPlainString();
    }

    public BigDecimal getOverpay() {
        if (getBalance().compareTo(BigDecimal.ZERO) > 0) {
            return getBalance();
        }

        return BigDecimal.ZERO;
    }

    public String getOverpayView() {
        return getOverpay().setScale(getPlan().getScale(), RoundingMode.HALF_UP).toPlainString();
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
