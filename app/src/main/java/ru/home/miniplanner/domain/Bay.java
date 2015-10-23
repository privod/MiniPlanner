package ru.home.miniplanner.domain;

import java.math.BigDecimal;

/**
 * Created by privod on 19.10.2015.
 */
public class Bay extends Domain {
    private BigDecimal cost;
    private SimpleCalendar dateReg;
    private String description;

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public SimpleCalendar getDateReg() {
        return dateReg;
    }

    public void setDateReg(SimpleCalendar dateReg) {
        this.dateReg = dateReg;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
