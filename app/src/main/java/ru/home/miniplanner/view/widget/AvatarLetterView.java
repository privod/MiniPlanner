package ru.home.miniplanner.view.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

import ru.home.miniplanner.R;
import ru.home.miniplanner.view.ViewService;

/**
 * Created by privod on 21.04.2016.
 */
public class AvatarLetterView extends TextView {
    private final GradientDrawable avatarDrawable = avatarDrawableInit(getResources());
    private final Drawable selectedDrawable = selectedDrawableInit(getResources());
    private final Animation animToSide = animToSideInit();
    private final Animation animFromSide = animFromSideInit();
    private CharSequence letter;
    private boolean selectedState;

    public AvatarLetterView(Context context) {
        this(context, null);
    }

    public AvatarLetterView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AvatarLetterView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AvatarLetterView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private GradientDrawable avatarDrawableInit(Resources resources) {

        GradientDrawable avatarDrawable = new GradientDrawable();
        avatarDrawable.setShape(GradientDrawable.OVAL);
        avatarDrawable.setColor(ViewService.getColor(getContext(), R.color.avatarGreen));
        setBackground(avatarDrawable);

        return avatarDrawable;
    }

    private Drawable selectedDrawableInit(Resources resources) {
        Bitmap bitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_checkbox_marked_circle);
        BitmapDrawable drawable = new BitmapDrawable(resources, bitmap);
        return drawable;
    }

    private Animation animToSideInit() {
        AnimationSet animToSide = new AnimationSet(true);
        animToSide.setDuration(100);
        ScaleAnimation scale = new ScaleAnimation(1.0f, 0.0f, 1.0f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        animToSide.addAnimation(scale);
        animToSide.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                setSelectedState(!isSelectedState());
//                switchSelectedState();
                startAnimation(animFromSide);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        return animToSide;
    }

    private Animation animFromSideInit() {
        AnimationSet animFromSide = new AnimationSet(true);
        animFromSide.setDuration(100);
        ScaleAnimation scale = new ScaleAnimation(0.0f, 1.0f, 1.0f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        animFromSide.addAnimation(scale);
        return animFromSide;
    }

    public void setLetter(String text) {
        char ch = text.toCharArray()[0];
        letter = String.valueOf(Character.toUpperCase(ch));
    }

    public void setStateSelected() {
        setBackground(selectedDrawable);
        setText("");
        selectedState = true;
    }

    public void setStateUnselected() {
        setBackground(avatarDrawable);
        setText(letter);
        selectedState = false;
    }

//    public void switchSelectedState() {
//        if (isSelectedState()) {
//            setStateUnselected();
//        } else {
//            setStateSelected();
//        }
//    }

    public void setSelectedState(boolean selectedState) {
        if (selectedState) {
            setStateSelected();
        } else {
            setStateUnselected();
        }
    }

    public boolean isSelectedState() {
        return selectedState;
    }

    public void AnimationSwitchSelectedState() {
        this.startAnimation(animToSide);
    }

    public GradientDrawable getAvatarDrawable() {
        return avatarDrawable;
    }
}
