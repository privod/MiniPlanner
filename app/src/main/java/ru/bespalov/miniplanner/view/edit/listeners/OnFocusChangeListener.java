package ru.bespalov.miniplanner.view.edit.listeners;

import android.support.design.widget.TextInputLayout;
import android.view.View;

/**
 * Created by privod on 31.05.2017.
 */

public abstract class OnFocusChangeListener implements View.OnFocusChangeListener {
    protected TextInputLayout inputLayout;

    protected OnFocusChangeListener(TextInputLayout inputLayout) {
        this.inputLayout = inputLayout;
    }
}
