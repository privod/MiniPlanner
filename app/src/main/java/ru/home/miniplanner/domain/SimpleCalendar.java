package ru.home.miniplanner.domain;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by privod on 21.10.2015.
 */
public class SimpleCalendar extends GregorianCalendar {

    public SimpleCalendar() {
        super();
    }

    public SimpleCalendar(String date) throws ParseException {
        this("dd.MM.yyyy", date);
    }

    public SimpleCalendar(String format, String date) throws ParseException {
        super();
        SimpleDateFormat sdf = new SimpleDateFormat(format, new Locale("ru"));
        this.setTime(sdf.parse(date));
    }

    @Override
    public String toString() {
        return this.toString("dd.MM.yyyy");
    }

//    public String toSQLiteDate() {
//        return this.toString("yyyy-MM-dd");
//    }

    public String toString(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, new Locale("ru"));
        return sdf.format(this.getTime());
    }
}
