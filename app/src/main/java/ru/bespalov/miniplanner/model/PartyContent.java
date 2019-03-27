package ru.bespalov.miniplanner.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by privod on 13.03.2017.
 */

public abstract class PartyContent extends Domain {

    @DatabaseField(dataType = DataType.BIG_DECIMAL)
    private BigDecimal sum;
    @DatabaseField(dataType = DataType.DATE)
    private Date dateReg;

    PartyContent() {
        this.sum = new BigDecimal("0");
        this.dateReg = new Date();
    }

    public abstract String getDescription();

    public BigDecimal getSum() {
        return sum;
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

}
