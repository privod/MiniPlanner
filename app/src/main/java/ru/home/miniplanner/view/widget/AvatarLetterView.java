package ru.home.miniplanner.view.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

import ru.home.miniplanner.R;

/**
 * Created by privod on 21.04.2016.
 */
public class AvatarLetterView extends TextView {
    public AvatarLetterView(Context context) {
        super(context);
        setBackground(context);
    }

    public AvatarLetterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setBackground(context);
    }

    public AvatarLetterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setBackground(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AvatarLetterView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setBackground(context);
    }

    public void setBackground(Context context) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.OVAL);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            drawable.setColor(context.getResources().getColor(R.color.avatarGreen, context.getTheme()));
        } else {
            drawable.setColor(context.getResources().getColor(R.color.avatarGreen));
        }

        this.setBackground(drawable);
    }
}
