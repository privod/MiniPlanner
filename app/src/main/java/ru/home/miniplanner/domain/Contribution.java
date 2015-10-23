package ru.home.miniplanner.domain;

import java.math.BigDecimal;

/**
 * Created by privod on 19.10.2015.
 */
public class Contribution extends Domain {
    private BigDecimal sum;
    private SimpleCalendar dateReg;
    private String description;
    private Party whom;

    public Contribution() {
        super(table);
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
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

    public Party getWhom() {
        return whom;
    }

    public void setWhom(Party whom) {
        this.whom = whom;
    }
}
