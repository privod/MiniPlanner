package ru.bespalov.miniplanner.model;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by privod on 19.10.2015.
 */
public class Contribution extends PartyContent {

//    @DatabaseField(foreign = true, foreignAutoRefresh = true)
//    private Party from;
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Party to;

    public Party getFrom() {
        return super.getParty();
    }

    public void setFrom(Party from) {
        super.setParty(from);
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
