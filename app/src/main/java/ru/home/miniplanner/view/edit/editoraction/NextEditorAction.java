package ru.home.miniplanner.view.edit.editoraction;

import android.widget.EditText;

/**
 * Created by privod on 30.05.2017.
 */

public class NextEditorAction implements EditorAction {

    private EditText nextView;

    public NextEditorAction(EditText nextView) {
        this.nextView = nextView;
    }

    @Override
    public boolean action() {
        if (null != nextView) {
            nextView.requestFocus();
            nextView.selectAll();
            return true;
        }

        return false;
    }
}
