package ru.home.miniplanner.view.edit;

import android.util.SparseArray;
import android.view.KeyEvent;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

/**
 * Created by privod on 25.05.2017.
 */

public class OnEditorActionListenerManager implements TextView.OnEditorActionListener {

    private SparseArray <List<TextView.OnEditorActionListener>> listeners;

    public OnEditorActionListenerManager() {
        this.listeners = new SparseArray<>();
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        boolean result = true;
        List<TextView.OnEditorActionListener> list = listeners.get(actionId);
        for (TextView.OnEditorActionListener listener: list) {
            result = result & listener.onEditorAction(textView, actionId, keyEvent);
        }

        return result;
    }

    public void addListener(int actionId, TextView.OnEditorActionListener listener) {

        List<TextView.OnEditorActionListener> list = listeners.get(actionId);
        if (null == list) {
            list = new ArrayList<>();
            listeners.put(actionId, list);
        }

        list.add(listener);
    }

    public void removeListener(int actionId, TextView.OnEditorActionListener listener) {
        listeners.get(actionId).remove(listener);
    }

}
