package ru.home.miniplanner.view.adapter;

import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ru.home.miniplanner.model.Domain;

/**
 * Created by privod on 19.10.2015.
 */
public abstract class BaseAdapter<VH extends BaseAdapter.ViewHolder, T extends Domain> extends android.widget.BaseAdapter {

    public abstract VH onCreateViewHolder(ViewGroup parent);
    public abstract void onBindViewHolder(VH holder, int position, ViewGroup parent);

    public static class ViewHolder {
        View itemView;

        public ViewHolder(View itemView) {
            this.itemView = itemView;
        }

        public View getItemView() {
            return itemView;
        }
    }

    //    private Context context;
    private List<T> data;
    private SparseBooleanArray arrayChecked;
    protected Class<VH> tClass;

    public BaseAdapter(Class<VH> tClass) {
        this.data = new ArrayList<>();
        this.arrayChecked = new SparseBooleanArray();
        this.tClass = tClass;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public T getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        VH holder;

        if (convertView == null) {
            holder = onCreateViewHolder(parent);
            convertView = holder.getItemView();
        } else {
            holder = tClass.cast(convertView.getTag());
        }

        onBindViewHolder(holder, position, parent);
        convertView.setTag(holder);

        return convertView;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public void setData(Collection<T> c) {
        this.data = new ArrayList<>(c);
    }

    public List<T> getData() {
        return data;
    }

    public SparseBooleanArray getArrayChecked() {
        return arrayChecked;
    }

    public void setArrayChecked(SparseBooleanArray arrayChecked) {
        this.arrayChecked = arrayChecked;
    }
}
