package ru.home.miniplanner.model;

import android.support.annotation.ColorInt;

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
    public static final String EXTRA_NAME = "plan";

    @DatabaseField
    private String name;
    @DatabaseField(dataType = DataType.DATE)
    private Date dateReg;
    @DatabaseField(dataType = DataType.BIG_DECIMAL)
    private BigDecimal costExpect;
    @DatabaseField(dataType = DataType.INTEGER)
    @ColorInt
    private int avatarColor;
    @DatabaseField(dataType = DataType.BOOLEAN)
    private boolean selected;
    @ForeignCollectionField
    private Collection<Party> parties;

    public Plan() {
        dateReg = new Date();
        costExpect = new BigDecimal("0");
    }

    public BigDecimal getTotalCost() {
        BigDecimal totalCost = new BigDecimal("0");
        for (Party party : parties) {
            totalCost = totalCost.add(party.getTotalCostBays());
        }
        return totalCost;
    }

    public BigDecimal getShare() {
        BigDecimal partiesCount = new BigDecimal(parties.size());
        return getTotalCost().divide(partiesCount, 0, RoundingMode.HALF_UP);
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

//    public List<Party> getParties() {
//        if (parties instanceof List) {
//            return (List<Party>)parties;
//        } else {
//            return new ArrayList<Party>(parties);
//        }
//    }
//
//    public void setParties(Collection<Party> parties) {
//        this.parties = parties;
//    }

    public Collection<Party> getParties() {
        return parties;
    }

    public void setParties(Collection<Party> parties) {
        this.parties = parties;
    }

    public BigDecimal getCostExpect() {
        return costExpect;
    }

    public void setCostExpect(BigDecimal costExpect) {
        this.costExpect = costExpect;
    }

    public int getAvatarColor() {
        return avatarColor;
    }

    public void setAvatarColor(int avatarColor) {
        this.avatarColor = avatarColor;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
