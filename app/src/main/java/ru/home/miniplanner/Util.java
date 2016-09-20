package ru.home.miniplanner;

import android.util.Log;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import ru.home.miniplanner.model.Domain;

/**
 * Created by privod on 24.08.2016.
 */
public class Util {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", new Locale("ru"));

    public static String getLogTag(Objects objects) {
        return objects.getClass().getSimpleName();
    }

    public static String dateToString(Date date) {
        return dateFormat.format(date);
    }

    public static Date dateParse(String date) {
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            Log.e(Util.class.getSimpleName(), e.getMessage());
            return null;
        }
    }

    public static <T extends Domain> int positionById(List<T> list, long id) {
        for (int position = 0; position < list.size(); position++) {
            if (list.get(position).getId() == id) {
                return position;
            }
        }

        return -1;
    }
}
