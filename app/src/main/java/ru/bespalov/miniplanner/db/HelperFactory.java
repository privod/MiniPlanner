package ru.bespalov.miniplanner.db;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;

/**
 * Created by privod on 23.10.2015.
 */
public class HelperFactory {

    private static DatabaseHelper helper;

    public static DatabaseHelper getHelper() {
        return helper;
    }

    public static void setHelper(Context context) {
        helper = OpenHelperManager.getHelper(context, DatabaseHelper.class);
    }

    public static void releaseHelper() {
        OpenHelperManager.releaseHelper();
        helper = null;
    }
}
