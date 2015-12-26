package ru.home.miniplanner.service;

import android.util.Log;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

import ru.home.miniplanner.model.Bay;
import ru.home.miniplanner.model.Contribution;

/**
 * Created by privod on 23.10.2015.
 */
public class ContributionDao extends BaseDaoImpl<Contribution, Long> {

    private static final String TAG = ContributionDao.class.getSimpleName();

    public ContributionDao(ConnectionSource connectionSource, Class<Contribution> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public int save(Contribution contribution) {
        try {
            if (null == contribution.getId()) {
                return super.create(contribution);
            } else {
                return super.update(contribution);
            }
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
            return 0;
        }
    }

    public int delete(Long id) {
        return this.delete(getById(id));
    }

    public int delete(Contribution contribution) {
        try {
            return super.delete(contribution);
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
            return 0;
        }
    }

    public Contribution getById(Long id) {
        try {
            return super.queryForId(id);
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
            return null;
        }
    }
}
