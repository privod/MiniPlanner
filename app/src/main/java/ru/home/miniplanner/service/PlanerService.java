package ru.home.miniplanner.service;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;

import ru.home.miniplanner.db.DBHelper;
import ru.home.miniplanner.domain.Domain;
import ru.home.miniplanner.domain.Plan;
import ru.home.miniplanner.domain.SimpleCalendar;
import ru.home.miniplanner.service.factory.DomainFactory;

/**
 * Created by privod on 20.10.2015.
 */
public class PlanerService {

    final String LOG_TAG = "PlanerService";

    SQLiteDatabase db;

    public PlanerService(DBHelper helper) {
        this.db = helper.getWritableDatabase();
    }

    public <T extends Domain> void  save(T entity) {

        Long id = entity.getId();
        String table = entity.getTable();

        if (null == id) {
            Log.d(LOG_TAG, String.format("--- Insert in %s: ---", table));
            id = db.insert(table, null, entity.getContentValues());
            Log.d(LOG_TAG, "row inserted, ID = " + id);
            entity.setId(id);
        } else {
            Log.d(LOG_TAG, String.format("--- Update %s: ---", table));
            db.update(table, entity.getContentValues(), "id = ?", new String[]{id.toString()});
            Log.d(LOG_TAG, "updated row, ID = " + id);
        }
    }

    public <T extends Domain> ArrayList<T> PlansGetAll(DomainFactory factory) {
        ArrayList<T> list = new ArrayList<>();
//        T entity = (T)factory.getInstance();
//        list.add(entity);

        Log.d(LOG_TAG, "--- Select from plans: ---");
        Cursor c = db.query("mytable", null, null, null, null, null, null);

        if (!c.moveToFirst()) {
            Log.d(LOG_TAG, "Table plans is empty");
            return list;
        }

        do {
            Plan plan = new Plan();
            String[] columns = c.getColumnNames();
            for (int col = 0; col < columns.length; col++) {
                Object obj = c.getString(col);
                plan.set
            }
            plan.setName(c.getString(c.getColumnIndex("name")));
            try {
                plan.setDateReg(new SimpleCalendar("yyyy-MM-dd", c.getString(c.getColumnIndex("date_reg"))));
            } catch (ParseException e) {
                Log.e(LOG_TAG, e.getMessage());
            }
            plan.setCostExpect(new BigDecimal(c.getString(c.getColumnIndex("cost_expect"))));
            list.add(plan);
        } while (c.moveToNext());

        return list;
    }

  /*  public void PlanSave(Plan plan) {

        ContentValues cv = new ContentValues();
        cv.put("name", plan.getName());
        cv.put("DateReg", dateToString(plan.getDateReg()));
        cv.put("name", plan.getCostExpect().doubleValue());

        save("plans", cv, plan.getId());
    }*/
}
