package ru.home.miniplanner.view.edit;

import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by privod on 01.11.2015.
 */
public class OnEditorActionTabBehavior implements TextView.OnEditorActionListener {
    static final String LOG_TAG = OnEditorActionTabBehavior.class.getSimpleName();

    EditText nextView;
    private OnEditorActionDoneListener doneListener;
//    abstract protected void editResultOk();

    public OnEditorActionTabBehavior(EditText nextView, OnEditorActionDoneListener l) {
        this.nextView = nextView;
        this.doneListener = l;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_NEXT) {
            if (null == nextView) {
                Log.e(LOG_TAG, "imeOptions last View can not be \"actionNext\", his mast be \"doneListener\"");
                return false;
            }
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
