package ru.home.miniplanner.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

import ru.home.miniplanner.R;
import ru.home.miniplanner.domain.Domain;

/**
 * Created by privod on 19.10.2015.
 */
public class PlannerAdapter<T extends Domain> extends BaseAdapter {

    private final Context context;
    private final LayoutInflater layout;
    private final List<T> list;

    public PlannerAdapter(Context context, List<T> list) {
        this.context = context;
        this.layout = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (null == convertView) {
            convertView = layout.inflate(R.layout.plan_view, parent, false);
        }

        return convertView;
    }
}
