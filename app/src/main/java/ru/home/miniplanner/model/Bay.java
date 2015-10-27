package ru.home.miniplanner.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by privod on 19.10.2015.
 */
public class Bay extends Domain {
    private BigDecimal cost;
    private Date dateReg;
    private String description;

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
}
