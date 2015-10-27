package ru.home.miniplanner.db;

import android.content.Context;
import java.sql.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import ru.home.miniplanner.model.Plan;
import ru.home.miniplanner.service.PlanDao;

/**
 * Created by privod on 23.10.2015.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String TAG = DatabaseHelper.class.getSimpleName();
    private static final String DATABASE_NAME ="planner.db";
    private static final int DATABASE_VERSION = 1;

    private PlanDao planDao;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Plan.class);
        }
        catch (SQLException e){
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
//        try
        {
            //TODO update tables.
        }
//        catch (SQLException e){
//            Log.e(TAG, e.getMessage());
//            throw new RuntimeException(e);
//        }
    }

    public synchronized PlanDao getPlanDao() {
        if (null == planDao) {
            try {
                planDao = new PlanDao(getConnectionSource(), Plan.class);
            } catch (SQLException e) {
                Log.e(TAG, e.getMessage());
            }
        }
        return planDao;
    }

    @Override
    public void close() {
        planDao = null;
        super.close();
    }
}
