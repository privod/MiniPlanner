package ru.home.miniplanner.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by privod on 20.10.2015.
 */
public class DBHelper extends SQLiteOpenHelper {

    final String LOG_TAG = "DBHelper";

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "planner";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(LOG_TAG, "--- onCreate database ---");

        db.execSQL("create table plans ("
                + "id integer primary key autoincrement,"
                + "name text,"
                + "date_reg date,"
                + "cost_expect double" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
