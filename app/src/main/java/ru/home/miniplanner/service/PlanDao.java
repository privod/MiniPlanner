package ru.home.miniplanner.service;

import android.util.Log;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

import ru.home.miniplanner.model.Plan;

/**
 * Created by privod on 23.10.2015.
 */
public class PlanDao extends BaseDaoImpl<Plan, Long> {

    private static final String TAG = PlanDao.class.getSimpleName();

    public PlanDao(ConnectionSource connectionSource, Class<Plan> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public int save(Plan plan) {
        try {
            if (null == plan.getId()) {
                return super.create(plan);
            } else {
                return super.update(plan);
            }
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
            return 0;
        }
    }

    public int delete(Long id) {
        return this.delete(getById(id));
    }

    @Override
    public int delete(Plan plan) {
        try {
            return super.delete(plan);
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
            return 0;
        }
    }

    @Override
    public int refresh(Plan plan) {
        try {
            return super.refresh(plan);
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
            return 0;
        }
    }

    public Plan getById(Long id) {
        try {
            return super.queryForId(id);
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
            return null;
        }
    }

    public List<Plan> getAll() {
        try {
            return this.queryForAll();
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
            return null;
        }
    }
}
