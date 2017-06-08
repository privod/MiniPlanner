package ru.bespalov.miniplanner.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by privod on 19.10.2015.
 */
@DatabaseTable
public class Bay extends PartyContent {

    @DatabaseField
    private String description;
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Party party;

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Party getParty() {
        return party;
    }

    public void setParty(Party party) {
        this.party = party;
    }
}
