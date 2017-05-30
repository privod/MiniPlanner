package ru.home.miniplanner.view.edit.editoraction;

import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by privod on 30.05.2017.
 */

public class NextEditorAction implements TextView.OnEditorActionListener {

    private EditText nextView;

    public NextEditorAction(EditText nextView) {
        this.nextView = nextView;
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if (null != nextView) {
            nextView.requestFocus();
            nextView.selectAll();
            return true;
        }

        return false;
    }
}
