package ru.home.miniplanner.view.edit.editoraction;

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

    private boolean actionCheck(TextView.OnEditorActionListener action, TextView v, int actionId, KeyEvent event) {
        return null != action && action.onEditorAction(v, actionId, event);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_NEXT) {
            return actionCheck(nextListener, v, actionId, event);
        } else  if (actionId == EditorInfo.IME_ACTION_DONE) {
            return actionCheck(doneListener, v, actionId, event);
        }
        return false;
    }
}
