package ru.home.miniplanner.db;

import android.util.Log;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

import ru.home.miniplanner.model.Domain;

/**
 * Created by privod on 23.10.2015.
 */
public class Dao<T extends Domain> extends BaseDaoImpl<T, Long> {

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

    public int delete(Long id) {
        return this.delete(getById(id));
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

    public T getById(Long id) {
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
}
