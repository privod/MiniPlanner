package ru.home.miniplanner.db;

import android.util.Log;

import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

import ru.home.miniplanner.model.Party;
import ru.home.miniplanner.model.Plan;

/**
 * Created by privod on 23.10.2015.
 */
public class PartyDao extends Dao<Party> {

    public static class Factory extends Dao.Factory<Party, PartyDao> {

        @Override
        public PartyDao newDaoInstance(ConnectionSource connectionSource) throws SQLException {
            return new PartyDao(connectionSource);
        }
    }

    public PartyDao(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, Party.class);
    }

    /**
     * Return all parties of current plan except yourself
     * @param planId Current plan id
     * @param partySelfId Yourself id
     */
    public List<Party> getOtherParty(Long planId, Long partySelfId) {
        try {
            return this.queryBuilder().where().eq("plan_id", planId).and().not().idEq(partySelfId).query(); // TODO Hardcode: "plan_id"
        } catch (SQLException e) {
            Log.e(this.getClass().getSimpleName(), e.getMessage());
            return null;
        }
    }

}
