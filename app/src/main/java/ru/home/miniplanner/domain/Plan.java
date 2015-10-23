package ru.home.miniplanner.domain;

import android.content.ContentValues;
import android.database.Cursor;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by privod on 19.10.2015.
 */
@DatabaseTable
public class Plan extends Domain {

    @DatabaseField
    private String name;
    @DatabaseField(dataType = DataType.DATE)
    private SimpleCalendar dateReg;
    @DatabaseField(dataType = DataType.BIG_DECIMAL)
    private BigDecimal costExpect;

    private List<Party> partners;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SimpleCalendar getDateReg() {
        return dateReg;
    }

    public void setDateReg(SimpleCalendar dateReg) {
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
