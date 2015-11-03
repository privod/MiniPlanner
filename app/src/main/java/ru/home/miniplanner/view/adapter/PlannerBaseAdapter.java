package ru.home.miniplanner.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import ru.home.miniplanner.R;
import ru.home.miniplanner.model.Domain;

/**
 * Created by privod on 19.10.2015.
 */
public class PlannerBaseAdapter<T extends Domain> extends BaseAdapter {

    private final Context context;
    private final LayoutInflater layout;
    private List<T> data;

    public PlannerBaseAdapter(Context context, List<T> list) {
        this.context = context;
        this.layout = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        this.data = (null != list) ? list : new ArrayList<T>();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (null == convertView) {
            convertView = layout.inflate(R.layout.plan_view, parent, false);
        }

        return convertView;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
