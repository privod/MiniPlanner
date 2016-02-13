package ru.home.miniplanner.model;

import com.j256.ormlite.field.DatabaseField;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by privod on 19.10.2015.
 */
public class Contribution extends Domain {
    public static final String EXTRA_NAME = "contribution";

    private BigDecimal sum;
    private Date dateReg;
    private String description;
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Party from;
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Party to;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
