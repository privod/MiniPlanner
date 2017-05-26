package ru.home.miniplanner.view.edit;

import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by privod on 01.11.2015.
 */
class OnEditorActionTabBehavior implements TextView.OnEditorActionListener {

    private EditText nextView;
    private OnEditorActionDoneListener doneListener;

    OnEditorActionTabBehavior(EditText nextView, OnEditorActionDoneListener listener) {
        this.nextView = nextView;
        this.doneListener = listener;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_NEXT) {
            nextView.requestFocus();
            nextView.selectAll();
            return true;
        } else  if (actionId == EditorInfo.IME_ACTION_DONE) {
            doneListener.onActionDone();
            return true;
        }
        return false;
    }
}
