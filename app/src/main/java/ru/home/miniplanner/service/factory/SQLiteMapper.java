package ru.home.miniplanner.service.factory;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by privod on 22.10.2015.
 */
public interface SQLiteMapper {

    public ContentValues getContentValues();

    public void cursorMapper(Cursor cursor);
}
