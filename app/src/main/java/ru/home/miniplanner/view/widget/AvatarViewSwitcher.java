package ru.home.miniplanner.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.widget.ViewAnimator;
import android.widget.ViewSwitcher;

/**
 * Created by privod on 10.09.2016.
 */
public class AvatarViewSwitcher extends ViewSwitcher {
    public AvatarViewSwitcher(Context context) {
        super(context);
    }

    public AvatarViewSwitcher(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setDisplayedChildNoAnim(int whichChild) {

        Animation inAnimation = getInAnimation();
        Animation outAnimation = getOutAnimation();
        setInAnimation(null);
        setOutAnimation(null);
        setDisplayedChild(whichChild);
        setInAnimation(inAnimation);
        setOutAnimation(outAnimation);
    }
}
