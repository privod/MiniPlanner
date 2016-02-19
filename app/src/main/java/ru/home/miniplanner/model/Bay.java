package ru.home.miniplanner.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.math.BigDecimal;
import java.text.Format;
import java.util.Date;

/**
 * Created by privod on 19.10.2015.
 */
@DatabaseTable
public class Bay extends Domain {
    public static final String EXTRA_NAME = "bay";

    @DatabaseField(dataType = DataType.BIG_DECIMAL)
    private BigDecimal cost;
    @DatabaseField(dataType = DataType.DATE)
    private Date dateReg;
    @DatabaseField
    private String description;
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Party party;

    public Bay() {
        this.cost = new BigDecimal("0");
        this.dateReg = new Date();
//        this.description = null;
    }

    @Override
    public String toString() {
        return String.format("%s/t%s", getDescription(), getCost());
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public Date getDateReg() {
        return dateReg;
    }

    public void setDateReg(Date dateReg) {
        this.dateReg = dateReg;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Party getParty() {
        return party;
    }

    public void setParty(Party party) {
        this.party = party;
    }
}
