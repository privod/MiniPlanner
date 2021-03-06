package ru.bespalov.miniplanner.db;

import android.util.Log;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

import ru.bespalov.miniplanner.model.Domain;

/**
 * Created by privod on 23.10.2015.
 */
public class Dao<T extends Domain> extends BaseDaoImpl<T, Long> {

    static abstract class Factory<T extends Domain, D extends Dao<T>> {
        public abstract D newDaoInstance(ConnectionSource connectionSource) throws SQLException;
    }

    public Dao(ConnectionSource connectionSource, Class<T> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public int save(T entity) {
        try {
            if (null == entity.getId()) {
                return super.create(entity);
            } else {
                return super.update(entity);
            }
        } catch (SQLException e) {
            Log.e(this.getClass().getSimpleName(), e.getMessage());
            return 0;
        }
    }

    @Override
    public int delete(T entity) {
        try {
            return super.delete(entity);
        } catch (SQLException e) {
            Log.e(this.getClass().getSimpleName(), e.getMessage());
            return 0;
        }
    }

//    @Override
    public int refresh(T entity) {
        try {
            return super.refresh(entity);
        } catch (SQLException e) {
            Log.e(this.getClass().getSimpleName(), e.getMessage());
            return 0;
        }
    }

    private T getById(Long id) {
        try {
            return super.queryForId(id);
        } catch (SQLException e) {
            Log.e(this.getClass().getSimpleName(), e.getMessage());
            return null;
        }
    }

    public List<T> getAll() {
        try {
            return this.queryForAll();
        } catch (SQLException e) {
            Log.e(this.getClass().getSimpleName(), e.getMessage());
            return null;
        }
    }

    public List<T> getAllSorted(String columnName, boolean ascending) {
        try {
            return this.queryBuilder().orderBy(columnName, ascending).query();
        } catch (SQLException e) {
            Log.e(this.getClass().getSimpleName(), e.getMessage());
            return null;
        }
    }
}
