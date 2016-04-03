package ru.home.miniplanner.view.listeners;

import android.view.View;

/**
 * Created by bespalov on 01.04.16.
 */
public interface ClickListener {

    void onClick(View view, int position);

    void onLongClick(View view, int position);
}
