package ru.home.miniplanner.view.edit;

import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import ru.home.miniplanner.view.edit.editoraction.EditorAction;

/**
 * Created by privod on 01.11.2015.
 */
class OnEditorActionTabBehavior implements TextView.OnEditorActionListener {

    private EditText nextView;
    private EditorAction doneBehavior;

    OnEditorActionTabBehavior(EditText nextView, EditorAction done) {
        this.nextView = nextView;
        this.doneBehavior = done;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_NEXT) {
            nextView.requestFocus();
            nextView.selectAll();
            return true;
        } else  if (actionId == EditorInfo.IME_ACTION_DONE) {
            doneBehavior.action();
            return true;
        }
        return false;
    }
}
