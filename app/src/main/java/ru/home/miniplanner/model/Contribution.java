package ru.home.miniplanner.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by privod on 19.10.2015.
 */
public class Contribution extends PartyContent {
//    public static final String EXTRA_NAME = "contribution";

//    @DatabaseField(dataType = DataType.BIG_DECIMAL)
//    private BigDecimal sum;
//    @DatabaseField(dataType = DataType.DATE)
//    private Date dateReg;

//    private String description;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Party from;
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Party to;

//    public Contribution() {
//        this.sum = new BigDecimal("0");
//        this.dateReg = new Date();
//    }

//    public String toString() {
//        return String.format("%s/t%s", getTo(), getSum());
//    }

//    public BigDecimal getSum() {
//        return sum;
//    }
//
//    public void setSum(BigDecimal sum) {
//        this.sum = sum;
//    }
//
//    public Date getDateReg() {
//        return dateReg;
//    }
//
//    public void setDateReg(Date dateReg) {
//        this.dateReg = dateReg;
//    }

//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }

    public Party getFrom() {
        return from;
    }

    public void setFrom(Party from) {
        this.from = from;
    }

    public Party getTo() {
        return to;
    }

    public void setTo(Party to) {
        this.to = to;
    }

    @Override
    public String getDescription() {
        return to.getName();
    }
}
