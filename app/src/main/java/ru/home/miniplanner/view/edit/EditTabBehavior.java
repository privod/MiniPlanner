package ru.home.miniplanner.view.edit;

import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import ru.home.miniplanner.view.PlanEditActivity;

/**
 * Created by privod on 01.11.2015.
 */
public class EditTabBehavior implements TextView.OnEditorActionListener {
    static final String LOG_TAG = EditTabBehavior.class.getSimpleName();

    EditText nextView;
    private OnActionDoneListener doneListener;
//    abstract protected void editResultOk();

    public EditTabBehavior(EditText nextView, OnActionDoneListener listener) {
        this.nextView = nextView;
        this.doneListener = listener;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_NEXT) {
            if (null != nextView) {
                Log.e(LOG_TAG, "imeOptions last View can not be \"actionNext\", his mast be \"actionDone\"");
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

interface OnActionDoneListener {
    public void onActionDone();
}