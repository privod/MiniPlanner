package ru.home.miniplanner.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.View;

import com.bignerdranch.android.multiselector.MultiSelector;
import com.bignerdranch.android.multiselector.SwappingHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

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
        this.updateData(new ArrayList<T>());
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

    public int getPositionById(long id) {
        for (int position = 0; position < this.data.size(); position++) {
            if (this.data.get(position).getId() == id) {
                return position;
            }
        }

        return -1;
    }

    public void updateData(List<T> newData) {
        Set<Integer> updatedPosition = new HashSet<>();
        for (int i = 0; i < newData.size(); i++) {
            T itemNew = newData.get(i);
            int position = getPositionById(itemNew.getId());
            if (position < 0) {
                this.data.add(itemNew);
                position = this.data.indexOf(itemNew);
                notifyItemInserted(position);
            } else {
//                T itemOld = this.data.get(position);
                this.data.set(position, itemNew);
                notifyItemChanged(position);
            }
            updatedPosition.add(position);
        }

        this.data.
        this.data = data;
    }

    public void updateData(Collection<T> c) {
        this.updateData(new ArrayList<>(c));
    }

    public List<T> getData() {
        return data;
    }

    public void remove(int position) {
        getData().remove(position);
        notifyItemRemoved(position);
    }
//
//    public void add(String text, int position) {
//        getData().add(position, text);
//        notifyItemInserted(position);
//    }
}
