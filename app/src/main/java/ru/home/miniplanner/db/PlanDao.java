package ru.home.miniplanner.db;

import android.util.Log;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

import ru.home.miniplanner.model.Plan;

/**
 * Created by privod on 23.10.2015.
 */
public class PlanDao extends BaseDao<Plan> {

    public static PlanDao newInstance(ConnectionSource connectionSource) throws SQLException {
        return new PlanDao(connectionSource);
    }

    public PlanDao(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, Plan.class);
    }

}
