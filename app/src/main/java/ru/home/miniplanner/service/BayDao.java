package ru.home.miniplanner.service;

import android.util.Log;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

import ru.home.miniplanner.model.Bay;
import ru.home.miniplanner.model.Party;

/**
 * Created by privod on 23.10.2015.
 */
public class BayDao extends BaseDaoImpl<Bay, Long> {

    private static final String TAG = BayDao.class.getSimpleName();

    public BayDao(ConnectionSource connectionSource, Class<Bay> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public int save(Bay bay) {
        try {
            if (null == bay.getId()) {
                return super.create(bay);
            } else {
                return super.update(bay);
            }
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
            return 0;
        }
    }

    public int delete(Long id) {
        return this.delete(getById(id));
    }

    public int delete(Bay bay) {
        try {
            return super.delete(bay);
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
            return 0;
        }
    }

    public Bay getById(Long id) {
        try {
            return super.queryForId(id);
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
            return null;
        }
    }

    public List<Bay> getByPartyId(Long partyId) {
        try {
            return this.queryBuilder().where().eq("party_id", partyId).query();
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
            return null;
        }
    }
}
