package ru.home.miniplanner.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by privod on 19.10.2015.
 */
@DatabaseTable
public class Plan extends Domain {

    @DatabaseField
    private String name;
    @DatabaseField(dataType = DataType.DATE)
    private Date dateReg;
    @DatabaseField(dataType = DataType.BIG_DECIMAL)
    private BigDecimal costExpect;
    @ForeignCollectionField
    private Collection<Party> parties;

    public Plan() {
        this.name = "";
        this.dateReg = new Date();
        this.costExpect = new BigDecimal("0");
    }

    public BigDecimal getTotalCost() {
        BigDecimal totalCost = new BigDecimal("0");
        for (Party party : parties) {
            totalCost = totalCost.add(party.getBaysTotalCost());
        }
        return totalCost;
    }
    public BigDecimal getShare() {
        return getTotalCost().divide(new BigDecimal(parties.size()));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateReg() {
        return dateReg;
    }

    public void setDateReg(Date dateReg) {
        this.dateReg = dateReg;
    }

    public List<Party> getParties() {
        if (parties instanceof List) {
            return (List<Party>)parties;
        } else {
            return new ArrayList<Party>(parties);
        }
    }

    public void setParties(List<Party> parties) {
        this.parties = parties;
    }

    public BigDecimal getCostExpect() {
        return costExpect;
    }

    public void setCostExpect(BigDecimal costExpect) {
        this.costExpect = costExpect;
    }
}
