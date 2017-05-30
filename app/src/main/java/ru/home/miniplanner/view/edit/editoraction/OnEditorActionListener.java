package ru.home.miniplanner.view.edit.editoraction;

import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by privod on 01.11.2015.
 */
public class OnEditorActionListener implements TextView.OnEditorActionListener {

//    private EditText nextView;
    private EditorAction nextAction;
    private EditorAction doneAction;
    private EditorAction goAction;

//    public OnEditorActionListener(EditText nextView, EditorAction doneAction, EditorAction goAction) {
    public OnEditorActionListener(EditorAction nextAction, EditorAction doneAction, EditorAction goAction) {
//        this.nextView = nextView;
        this.nextAction = nextAction;
        this.doneAction = doneAction;
        this.goAction = goAction;
    }

    private boolean actionCheck(EditorAction action) {
        return null != action && action.action();
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_NEXT) {
//            nextView.requestFocus();
//            nextView.selectAll();
            return actionCheck(nextAction);
        } else  if (actionId == EditorInfo.IME_ACTION_DONE) {
            return actionCheck(doneAction);
        } else  if (actionId == EditorInfo.IME_ACTION_GO) {
            return actionCheck(goAction);
        }
        return false;
    }
}
