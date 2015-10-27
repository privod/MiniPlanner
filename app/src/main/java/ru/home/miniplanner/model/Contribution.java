package ru.home.miniplanner.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by privod on 19.10.2015.
 */
public class Contribution extends Domain {
    private BigDecimal sum;
    private Date dateReg;
    private String description;
    private Party whom;

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

    public Party getWhom() {
        return whom;
    }

    public void setWhom(Party whom) {
        this.whom = whom;
    }
}
