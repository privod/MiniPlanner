package ru.home.miniplanner.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ru.home.miniplanner.model.Domain;
import ru.home.miniplanner.view.ViewService;

/**
 * Created by privod on 19.10.2015.
 */
public class PlannerBaseAdapter<T extends Domain> extends BaseAdapter {

    private final Context context;
    private final LayoutInflater layout;
    private final ViewService viewService;
    private List<T> list;

    public PlannerBaseAdapter(Context context) {
        this.context = context;
        this.layout = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        viewService = new ViewService(getContext());

        this.list = new ArrayList<T>();
    }

//    public PlannerBaseAdapter(Context context, List<T> list) {
//        this.context = context;
//        this.layout = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        viewService = new ViewService(getContext());
//
//        this.list = (null != list) ? list : new ArrayList<T>();
//    }
//
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
        return position;
//        return list.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return convertView;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public void setList(Collection<T> c) {
        this.list = new ArrayList<>(c);
    }

    public Context getContext() {
        return context;
    }

    public LayoutInflater getLayout() {
        return layout;
    }

    public ViewService getViewService() {
        return viewService;
    }
}
