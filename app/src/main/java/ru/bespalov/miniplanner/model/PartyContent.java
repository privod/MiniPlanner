package ru.bespalov.miniplanner.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

/**
 * Created by privod on 13.03.2017.
 */

public abstract class PartyContent extends Domain {

    @DatabaseField(dataType = DataType.BIG_DECIMAL)
    private BigDecimal sum;
    @DatabaseField(dataType = DataType.DATE)
    private Date dateReg;
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Party party;

    PartyContent() {
        this.sum = BigDecimal.ZERO;
        this.dateReg = new Date();
    }

    public abstract String getDescription();

    BigDecimal getSum() {
        return sum;
    }

    public String getSumView() {
        return getSum().setScale(getParty().getPlan().getScale(), RoundingMode.HALF_UP).toPlainString();
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public Date getDateReg() {
        return dateReg;
    }

    public void setDateReg(Date dateReg) {
        this.dateReg = dateReg;
    }

    protected Party getParty() {
        return party;
    }

    protected void setParty(Party party) {
        this.party = party;
    }
}
