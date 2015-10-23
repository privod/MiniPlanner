package ru.home.miniplanner.service;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

import ru.home.miniplanner.domain.Plan;

/**
 * Created by privod on 23.10.2015.
 */
public class PlanDao extends BaseDaoImpl<Plan, Long> {
    public PlanDao(ConnectionSource connectionSource, Class<Plan> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public List<Plan> getAllPlans() throws SQLException {
        return this.queryForAll();
    }
}
