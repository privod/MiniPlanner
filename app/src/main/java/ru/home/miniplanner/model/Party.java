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
    @DatabaseField(foreign = true)
    private Plan plan;
    private List<Bay> bays;
    private List<Contribution> contributions;

    public BigDecimal getDebt() {

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
