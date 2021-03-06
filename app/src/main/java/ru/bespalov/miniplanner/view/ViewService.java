package ru.bespalov.miniplanner.view;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by privod on 26.10.2015.
 */
public class ViewService {
    final String LOG_TAG = ViewService.class.getSimpleName();

    private final SimpleDateFormat dateRegFormat = new SimpleDateFormat("dd.MM.yyyy", new Locale("ru"));

    static public @ColorInt int getColor(Context context, @ColorRes int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return context.getResources().getColor(color, context.getTheme());
        } else {
            return context.getResources().getColor(color);
        }
    }

    static public void setStatusBar(Activity activity, @ColorRes int res) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            @ColorInt int color;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                color = activity.getResources().getColor(res, activity.getTheme());
            } else {
                color = activity.getResources().getColor(res);
            }
            window.setStatusBarColor(color);
        }
    }

    public String textViewGetString(TextView view) {
        return view.getText().toString();
    }

    public BigDecimal textViewGetDecimal(TextView view) {
        String decimal = textViewGetString(view);
        return new BigDecimal(decimal);
    }

    public Date textViewGetDate(TextView view) {
        String date = textViewGetString(view);
        try {
            return dateRegFormat.parse(date);
        } catch (ParseException e) {
            Log.e(LOG_TAG, e.getMessage());
            return null;
        }
    }

    public void textViewSetText(TextView view, String text) {
        view.setText(text);
    }

    public void textViewSetText(TextView view, BigDecimal decimal) {
        String text = decimal.toString();
        textViewSetText(view, text);
    }

    public void textViewSetText(TextView view, Date date) {
        String text = dateRegFormat.format(date);
        textViewSetText(view, text);
    }
}
