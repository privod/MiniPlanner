package ru.home.miniplanner.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bignerdranch.android.multiselector.MultiSelector;
import com.bignerdranch.android.multiselector.SwappingHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ru.home.miniplanner.model.Domain;

/**
 * Created by privod on 19.10.2015.
 */
public abstract class BaseAdapter<VH extends BaseAdapter.ViewHolder, T extends Domain> extends RecyclerView.Adapter<VH> {

    private List<T> data;
    protected Class<VH> tClass;

    protected MultiSelector multiSelector;

    public BaseAdapter(MultiSelector multiSelector, Class<VH> tClass) {
        this.multiSelector = multiSelector;
        this.data = new ArrayList<>();
        this.tClass = tClass;
    }

    public abstract class ViewHolder extends SwappingHolder {
        public ViewHolder(final View itemView) {
            super(itemView, multiSelector);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
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
}
