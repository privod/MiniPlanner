package ru.home.miniplanner.service;

import android.util.Log;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

import ru.home.miniplanner.model.Party;
import ru.home.miniplanner.model.Plan;

/**
 * Created by privod on 23.10.2015.
 */
public class PartyDao extends BaseDaoImpl<Party, Long> {

    private static final String TAG = PartyDao.class.getSimpleName();

    public PartyDao(ConnectionSource connectionSource, Class<Party> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public int save(Party party) {
        try {
            if (null == party.getId()) {
                return super.create(party);
            } else {
                return super.update(party);
            }
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
            return 0;
        }
    }

    public int delete(Long id) {
        return this.delete(getById(id));
    }

    public int delete(Party party) {
        try {
            return super.delete(party);
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
            return 0;
        }
    }

    @Override
    public int refresh(Party party) {
        try {
            return super.refresh(party);
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
            return 0;
        }
    }

    public Party getById(Long id) {
        try {
            return super.queryForId(id);
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
            return null;
        }
    }

    public List<Party> getByPlanId(Long planId) {
        try {
            return this.queryBuilder().where().eq("plan_id", planId).query();
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
            return null;
        }
    }
}
