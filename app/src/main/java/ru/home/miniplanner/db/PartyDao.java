package ru.home.miniplanner.db;

import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

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

}
