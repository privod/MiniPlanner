package ru.home.miniplanner.db;

import android.content.Context;
import java.sql.SQLException;
import java.util.List;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import ru.home.miniplanner.model.Bay;
import ru.home.miniplanner.model.Contribution;
import ru.home.miniplanner.model.Domain;
import ru.home.miniplanner.model.Party;
import ru.home.miniplanner.model.Plan;

/**
 * Created by privod on 23.10.2015.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static final String DATABASE_NAME ="planner.db";
    private static final int DATABASE_VERSION = 1;

    private Dao<Plan> planDao;
    private PartyDao partyDao;
    private Dao<Bay> bayDao;
    private Dao<Contribution> contributionDao;

//    private Dao<Plan> planDao;
//    private Dao<Party> partyDao;
//    private Dao<Bay> bayDao;
//    private Dao<Contribution> contributionDao;


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
        try
        {
            TableUtils.dropTable(connectionSource, Plan.class, true);
            TableUtils.createTable(connectionSource, Plan.class);
            TableUtils.dropTable(connectionSource, Party.class, true);
            TableUtils.createTable(connectionSource, Party.class);
            TableUtils.dropTable(connectionSource, Bay.class, true);
            TableUtils.createTable(connectionSource, Bay.class);
            TableUtils.dropTable(connectionSource, Contribution.class, true);
            TableUtils.createTable(connectionSource, Contribution.class);

            //TODO update tables with save data.
//            upgradeTable(database, getPlanDao(), Plan.class);
//            upgradeTable(database, getPartyDao(), Party.class);
//            upgradeTable(database, getBayDao(), Bay.class);
//            upgradeTable(database, getContributionDao(), Contribution.class);

        }
        catch (SQLException e){
            Log.e(this.getClass().getSimpleName(), e.getMessage());
            throw new RuntimeException(e);
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


    /*private <T extends Domain> Dao<T> daoCreate(Class<T> tClass) {
        Dao<T> dao = null;
        try {
            dao = new Dao<T>(getConnectionSource(), tClass);
        } catch (SQLException e) {
            Log.e(this.getClass().getSimpleName(), e.getMessage());
        }
        return dao;
    }

    public synchronized Dao<Plan> getPlanDao() {
        if (null == planDao) {
            planDao = daoCreate(Plan.class);
        }
        return planDao;
    }

    public synchronized Dao<Party> getPartyDao() {
        if (null == partyDao) {
            partyDao = daoCreate(Party.class);
        }
        return partyDao;
    }

    public synchronized Dao<Bay> getBayDao() {
        if (null == bayDao) {
            bayDao = daoCreate(Bay.class);
        }
        return bayDao;
    }

    public synchronized Dao<Contribution> getContributionDao() {
        if (null == contributionDao) {
            contributionDao = daoCreate(Contribution.class);
        }
        return contributionDao;
    }*/

    @Override
    public void close() {
        planDao = null;
        partyDao = null;
        bayDao = null;
        contributionDao = null;
        super.close();
    }
}
