package ru.home.miniplanner.db;

import android.content.Context;
import java.sql.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import ru.home.miniplanner.model.Bay;
import ru.home.miniplanner.model.Party;
import ru.home.miniplanner.model.Plan;
import ru.home.miniplanner.service.BayDao;
import ru.home.miniplanner.service.PartyDao;
import ru.home.miniplanner.service.PlanDao;

/**
 * Created by privod on 23.10.2015.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String TAG = DatabaseHelper.class.getSimpleName();
    private static final String DATABASE_NAME ="planner.db";
    private static final int DATABASE_VERSION = 6;

    private PlanDao planDao;
    private PartyDao partyDao;
    private BayDao bayDao;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Plan.class);
            TableUtils.createTable(connectionSource, Party.class);
            TableUtils.createTable(connectionSource, Bay.class);
        }
        catch (SQLException e){
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try
        {
            TableUtils.dropTable(connectionSource, Plan.class, true);
            TableUtils.createTable(connectionSource, Plan.class);
            TableUtils.dropTable(connectionSource, Party.class, true);
            TableUtils.createTable(connectionSource, Party.class);
            TableUtils.dropTable(connectionSource, Bay.class, true);
            TableUtils.createTable(connectionSource, Bay.class);

            //TODO update tables with save data.
        }
        catch (SQLException e){
            Log.e(TAG, e.getMessage());
            throw new RuntimeException(e);
        }
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

    public synchronized PartyDao getPartyDao() {
        if (null == partyDao) {
            try {
                partyDao = new PartyDao(getConnectionSource(), Party.class);
            } catch (SQLException e) {
                Log.e(TAG, e.getMessage());
            }
        }
        return partyDao;
    }

    public synchronized BayDao getBayDao() {
        if (null == bayDao) {
            try {
                bayDao = new BayDao(getConnectionSource(), Bay.class);
            } catch (SQLException e) {
                Log.e(TAG, e.getMessage());
            }
        }
        return bayDao;
    }

    @Override
    public void close() {
        planDao = null;
        partyDao = null;
        bayDao = null;
        super.close();
    }
}
