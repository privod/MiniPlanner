package ru.bespalov.miniplanner.db;

import android.content.ContentValues;
import android.content.Context;
import java.sql.SQLException;
import java.util.List;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import ru.bespalov.miniplanner.model.Bay;
import ru.bespalov.miniplanner.model.Contribution;
import ru.bespalov.miniplanner.model.Domain;
import ru.bespalov.miniplanner.model.Party;
import ru.bespalov.miniplanner.model.Plan;

/**
 * Created by privod on 23.10.2015.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    public static final String DATABASE_NAME ="planner.db";
    private static final int DATABASE_VERSION = 3;

    private Dao<Plan> planDao;
    private PartyDao partyDao;
    private Dao<Bay> bayDao;
    private Dao<Contribution> contributionDao;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Plan.class);
            TableUtils.createTable(connectionSource, Party.class);
            TableUtils.createTable(connectionSource, Bay.class);
            TableUtils.createTable(connectionSource, Contribution.class);
        }
        catch (SQLException e){
            Log.e(this.getClass().getSimpleName(), e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        ContentValues contVal = new ContentValues();

        database.beginTransaction();
        try
        {
            if (oldVersion < 2) {
                database.execSQL("ALTER TABLE `pla` ADD COLUMN `scale` INTEGER;");
                contVal.clear();
                contVal.put("scale", "0");                                                  // Default value for Plan.scale
                database.update("plan", contVal, null, null);

                database.execSQL("ALTER TABLE `party` ADD COLUMN `share` VARCHAR;");
                contVal.clear();
                contVal.put("share", "1");                                                  // Default value for Party.share
                database.update("party", contVal, null, null);
            }


            if (oldVersion < 3) {
                database.execSQL("ALTER TABLE `contribution` RENAME TO `contribution_bak`;");
                database.execSQL("CREATE TABLE `contribution` (`party_id` BIGINT , `to_id` BIGINT , `dateReg` VARCHAR , `sum` VARCHAR , `id` INTEGER PRIMARY KEY AUTOINCREMENT );");
                database.execSQL("INSERT INTO `contribution` SELECT `from_id`, `to_id`, `dateReg`, `sum`, `id` FROM `contribution_bak`;");
                database.execSQL("DROP TABLE `contribution_bak`;");
            }
        }
        catch (SQLiteException e){
            Log.e(this.getClass().getSimpleName(), e.getMessage());
            throw new RuntimeException(e);
        } finally {
            database.endTransaction();
        }
    }

    public <T extends Domain> void upgradeTable(SQLiteDatabase database, BaseDaoImpl dao, Class<T> type) throws SQLException {
        List<T> oldData = dao.queryForAll();
        TableUtils.dropTable(connectionSource, type, true);
        TableUtils.createTable(connectionSource, type);
        for (T entity :
                oldData) {
            dao.create(entity);
        }
    }

    private <T extends Domain, D extends Dao<T>> D getDao(D dao, Dao.Factory<T,D> factory) {
        if (null == dao) {
            try {
                dao = factory.newDaoInstance(getConnectionSource());
            } catch (SQLException e) {
                Log.e(this.getClass().getSimpleName(), e.getMessage());
            }
        }

        return dao;
    }

    public synchronized Dao<Plan> getPlanDao() {
        return getDao(planDao, new Dao.Factory<Plan, Dao<Plan>>() {
            @Override
            public Dao<Plan> newDaoInstance(ConnectionSource connectionSource) throws SQLException {
                return new Dao<Plan>(getConnectionSource(), Plan.class);
            }
        });
    }

    public synchronized PartyDao getPartyDao() {
        return getDao(partyDao, new PartyDao.Factory() );
    }

    public synchronized Dao<Bay> getBayDao() {
        return getDao(bayDao, new Dao.Factory<Bay, Dao<Bay>>() {
            @Override
            public Dao<Bay> newDaoInstance(ConnectionSource connectionSource) throws SQLException {
                return new Dao<Bay>(getConnectionSource(), Bay.class);
            }
        });
    }

    public synchronized Dao<Contribution> getContributionDao() {
        return getDao(contributionDao, new Dao.Factory<Contribution, Dao<Contribution>>() {
            @Override
            public Dao<Contribution> newDaoInstance(ConnectionSource connectionSource) throws SQLException {
                return new Dao<Contribution>(getConnectionSource(), Contribution.class);
            }
        });
    }

    @Override
    public void close() {
        planDao = null;
        partyDao = null;
        bayDao = null;
        contributionDao = null;
        super.close();
    }
}
