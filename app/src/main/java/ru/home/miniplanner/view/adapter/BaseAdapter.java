package ru.home.miniplanner.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Range;
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

import ru.home.miniplanner.Util;
import ru.home.miniplanner.model.Domain;
import ru.home.miniplanner.view.BaseListActivity;
import ru.home.miniplanner.view.PlansActivity;

/**
 * Created by privod on 19.10.2015.
 */
public abstract class BaseAdapter<VH extends BaseAdapter.ViewHolder, T extends Domain> extends RecyclerView.Adapter<VH> {

    private List<T> data;
    protected Class<VH> tClass;

    protected MultiSelector multiSelector;

    public BaseAdapter(MultiSelector multiSelector, Class<VH> tClass) {
        this.multiSelector = multiSelector;
        this.data = new ArrayList<T>();
        this.tClass = tClass;
    }

    public abstract class ViewHolder extends SwappingHolder {
        public ViewHolder(final View itemView) {
            super(itemView, multiSelector);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v.getContext() instanceof BaseListActivity) {
                        ((BaseListActivity) v.getContext()).startInsideActivity(getAdapterPosition());
                    }
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (v.getContext() instanceof BaseListActivity) {
                        ((BaseListActivity) v.getContext()).selectSwitch(ViewHolder.this);
                        return true;
                    }

                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    /*public int getPositionById(long id) {
        for (int position = 0; position < this.data.size(); position++) {
            if (this.data.get(position).getId() == id) {
                return position;
            }
        }

        return -1;
    }   // */

    public void updateData(List<T> newData) {
        this.data = newData;
        notifyDataSetChanged();

//        Set<Integer> updatedPosition = new HashSet<>();
        /*for (int position = 0; position < this.data.size(); position++) {
            T item = this.data.get(position);
            int positionNew = Util.positionById(newData, item.getId());
            if (positionNew < 0) {
                this.data.remove(item);
                notifyItemRemoved(position);
            } else {
                this.data.set(position, newData.get(positionNew));
                notifyItemChanged(position);
            }
            updatedPosition.add(positionNew);
        }

        for (int position = 0; position < newData.size(); position++) {
            if (!updatedPosition.contains(position)) {
                this.data.add(newData.get(position));
                notifyItemInserted(position);
            }
        } // */

        /*for (int i = 0; i < newData.size(); i++) {
            T itemNew = newData.get(i);
            int position = getPositionById(itemNew.getId());
            if (position < 0) {
                this.data.add(itemNew);
                position = this.data.indexOf(itemNew);
                notifyItemInserted(position);
            } else {
                this.data.set(position, itemNew);
                notifyItemChanged(position);
            }
            updatedPosition.add(position);
        } // */
    }

    public void updateData(Collection<T> c) {
        this.updateData(new ArrayList<>(c));
    }

    public List<T> getData() {
        return data;
    }
}
