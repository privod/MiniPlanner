package ru.home.miniplanner.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.math.BigDecimal;
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

    public Plan() {
        this.name = "";
        this.dateReg = new Date();
        this.costExpect = new BigDecimal("0");
    }

    private List<Party> partners;

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

    public List<Party> getPartners() {
        return partners;
    }

    public void setPartners(List<Party> partners) {
        this.partners = partners;
    }

    public BigDecimal getCostExpect() {
        return costExpect;
    }

    public void setCostExpect(BigDecimal costExpect) {
        this.costExpect = costExpect;
    }
}
