package ru.home.miniplanner.view.edit.listeners;

import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

/**
 * Created by privod on 01.11.2015.
 */
public class OnEditorActionListener implements TextView.OnEditorActionListener {

    private TextView.OnEditorActionListener nextListener;
    private TextView.OnEditorActionListener doneListener;

    public OnEditorActionListener(TextView.OnEditorActionListener nextListener, TextView.OnEditorActionListener doneListener) {
        this.nextListener = nextListener;
        this.doneListener = doneListener;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_NEXT) {
            return null != nextListener && nextListener.onEditorAction(v, actionId, event);
        } else  if (actionId == EditorInfo.IME_ACTION_DONE) {
            return  null != doneListener && doneListener.onEditorAction(v, actionId, event);
        }
        return false;
    }
}
