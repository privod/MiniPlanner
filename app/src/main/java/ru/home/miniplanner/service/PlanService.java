package ru.home.miniplanner.service;

import android.content.Context;
import android.util.Log;

import java.sql.SQLException;
import java.util.List;

import ru.home.miniplanner.db.HelperFactory;
import ru.home.miniplanner.model.Plan;

/**
 * Created by privod on 25.10.2015.
 */
public class PlanService {
    private Context context;

    PlanDao planDao;

    public PlanService(Context context) throws SQLException {
        this.context = context;

        HelperFactory.setHelper(context);
        planDao = HelperFactory.getHelper().getPlanDao();
    }

//    public List<Plan> getAllPlans() {
//
//    }
}
