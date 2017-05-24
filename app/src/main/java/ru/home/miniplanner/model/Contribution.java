package ru.home.miniplanner.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by privod on 19.10.2015.
 */
public class Contribution extends PartyContent {

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Party from;
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Party to;

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
