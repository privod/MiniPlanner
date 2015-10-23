package ru.home.miniplanner.service.factory;

import android.content.ContentValues;
import android.database.Cursor;

import ru.home.miniplanner.domain.Domain;
import ru.home.miniplanner.domain.Plan;

/**
 * Created by privod on 22.10.2015.
 */
public class PlanFactory implements DomainFactory, SQLiteMapper {
    @Override
    public Domain getInstance() {
        return new Plan();
    }

    @Override
    public ContentValues getContentValues() {
        ContentValues cv = new ContentValues();
        cv.put("name", getName());
        cv.put("date_reg", getDateReg().toSQLiteDate());
        cv.put("cost_expect", getCostExpect().toString());
        return cv;
    }

    @Override
    public void cursorMapper(Cursor cursor) {

    }}
