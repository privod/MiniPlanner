package ru.home.miniplanner.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.android.multiselector.MultiSelector;
import com.bignerdranch.android.multiselector.SwappingHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ru.home.miniplanner.model.Domain;
import ru.home.miniplanner.view.PlansActivity;

/**
 * Created by privod on 19.10.2015.
 */
public abstract class BaseAdapter<VH extends BaseAdapter.ViewHolder, T extends Domain> extends RecyclerView.Adapter<VH> {

//    public abstract VH onCreateViewHolder(ViewGroup parent);
//    public abstract void onBindViewHolder(VH holder, int position, ViewGroup parent);
    //    private Context context;
    private List<T> data;
//    private SparseBooleanArray arrayChecked;
    protected Class<VH> tClass;

    protected MultiSelector multiSelector;

    public BaseAdapter(MultiSelector multiSelector, Class<VH> tClass) {
        this.data = new ArrayList<>();
//        this.arrayChecked = new SparseBooleanArray();
        this.tClass = tClass;
    }

    public abstract class ViewHolder extends SwappingHolder {
//        View itemView;

        public ViewHolder(final View itemView) {
            super(itemView, multiSelector);

//            this.itemView = itemView;
        }

//        public View getItemView() {
//            return itemView;
//        }
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

//    public SparseBooleanArray getArrayChecked() {
//        return arrayChecked;
//    }
//
//    public void setArrayChecked(SparseBooleanArray arrayChecked) {
//        this.arrayChecked = arrayChecked;
//    }
}
