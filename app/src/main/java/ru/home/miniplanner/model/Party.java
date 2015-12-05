package ru.home.miniplanner.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by privod on 19.10.2015.
 */
@DatabaseTable
public class Party extends Domain {

    @DatabaseField
    private String name;
    @DatabaseField(dataType = DataType.BIG_DECIMAL)
    private BigDecimal deposit;                         // ?????
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Plan plan;
    private List<Bay> bays;
    private List<Contribution> contributions;
//    private List<Contribution> contributions;

    public BigDecimal getDebt() {
        BigDecimal summaryBays = new BigDecimal("0");
        for (Bay bay: bays ) {
            summaryBays.add(bay.getCost());
        }
        BigDecimal summaryContributions = new BigDecimal("0");
        for (Contribution contribution: contributions) {
            summaryContributions.add(contribution.getSum());
        }
        return plan.getShare().subtract(summaryBays);

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getDeposit() {
        return deposit;
    }

    public void setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public List<Bay> getBays() {
        return bays;
    }

    public void setBays(List<Bay> bays) {
        this.bays = bays;
    }

    public List<Contribution> getContributions() {
        return contributions;
    }

    public void setContributions(List<Contribution> contributions) {
        this.contributions = contributions;
    }
}
