package ru.bespalov.miniplanner.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.Date;

/**
 * Created by privod on 19.10.2015.
 */
@DatabaseTable
public class Plan extends Domain {

    @DatabaseField
    private String name;
    @DatabaseField(dataType = DataType.DATE)
    private Date dateReg;
    @DatabaseField(defaultValue = "0")
    private int scale;
    @ForeignCollectionField
    private Collection<Party> parties;

    public Plan() {
        dateReg = new Date();
//        scale = 0;
    }

    public BigDecimal getTotalCost() {
        BigDecimal totalCost = new BigDecimal("0");
        for (Party party : parties) {
            totalCost = totalCost.add(party.getBaysCost());
        }
        return totalCost;
    }

    public BigDecimal getPartiesCount() {
        BigDecimal partiesCount = new BigDecimal("0");
        for (Party party: parties) {
            partiesCount = partiesCount.add(party.getShare());
        }
        return partiesCount;
    }

    public BigDecimal getShareCost() {
        return getTotalCost().divide(getPartiesCount(), scale, RoundingMode.HALF_UP);
    }

    @Override
    public String toString() {
        return getName();
    }

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

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public Collection<Party> getParties() {
        return parties;
    }

    public void setParties(Collection<Party> parties) {
        this.parties = parties;
    }
}
