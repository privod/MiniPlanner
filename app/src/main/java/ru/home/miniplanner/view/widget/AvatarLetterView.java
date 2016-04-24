package ru.home.miniplanner.view.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
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
        this.setBackground(drawable);
        this.setBackgroundColorByRes(R.color.avatarGreen);
    }

    public void setBackgroundColor(@ColorInt int color){
        if (this.getBackground() instanceof GradientDrawable) {
            ((GradientDrawable)getBackground()).setColor(color);
        }

    }
    public void setBackgroundColorByRes(@ColorRes int color){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.setBackgroundColor(getContext().getResources().getColor(color, getContext().getTheme()));
        } else {
            this.setBackgroundColor(getContext().getResources().getColor(color));
        }
    }
}
