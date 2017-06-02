package ru.home.miniplanner.view.edit.listeners;

import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by privod on 30.05.2017.
 */

// TODO Вместо данного листенера попробовать применить стандартный механизм табуляции
// https://stackoverflow.com/questions/17989733/move-to-another-edittext-when-soft-keyboard-next-is-clicked-on-android

public class OnEditorActionListenerNext implements TextView.OnEditorActionListener {

    private View nextView;

    public OnEditorActionListenerNext(View nextView) {
        this.nextView = nextView;
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if (null != nextView) {
            nextView.requestFocus();
            if (nextView instanceof EditText) {
                ((EditText) nextView).selectAll();
            }
            return true;
        }

        return false;
    }
}
