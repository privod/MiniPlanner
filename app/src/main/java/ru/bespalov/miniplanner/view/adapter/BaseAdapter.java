package ru.bespalov.miniplanner.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.bignerdranch.android.multiselector.MultiSelector;
import com.bignerdranch.android.multiselector.SwappingHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ru.bespalov.miniplanner.R;
import ru.bespalov.miniplanner.model.Domain;
import ru.bespalov.miniplanner.view.widget.AvatarViewSwitcher;

/**
 * Created by privod on 19.10.2015.
 */
public abstract class BaseAdapter<VH extends BaseAdapter.ViewHolder, T extends Domain> extends RecyclerView.Adapter<VH> {

    private List<T> data;

    protected MultiSelector multiSelector;
    protected ItemAction itemAction;

    BaseAdapter(MultiSelector multiSelector) {
        this.multiSelector = multiSelector;
        this.data = new ArrayList<T>();
    }

    public interface ItemAction {

        void open(int position);

        void selectSwitch(BaseAdapter.ViewHolder holder);

        void edit(int position);
    }

    public abstract class ViewHolder extends SwappingHolder {
        AvatarViewSwitcher avatarViewSwitcher;

        ViewHolder(final View itemView) {
            super(itemView, multiSelector);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemAction.open(getAdapterPosition());
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    itemAction.selectSwitch(ViewHolder.this);

                    return true;
                }
            });
            avatarViewSwitcher = (AvatarViewSwitcher) itemView.findViewById(R.id.view_switcher_avatar);
            avatarViewSwitcher.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemAction.selectSwitch(ViewHolder.this);
                }
            });
        }
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {

        if (multiSelector.isSelected(position, holder.getItemId())) {
            holder.avatarViewSwitcher.setDisplayedChildNoAnim(1);
        } else {
            holder.avatarViewSwitcher.setDisplayedChildNoAnim(0);
        }
    }

    TextDrawable newAvatarDrawable(String text) {
        String letter = String.valueOf(Character.toUpperCase(text.charAt(0)));
        return TextDrawable.builder().buildRound(letter, ColorGenerator.MATERIAL.getColor(letter));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void updateData(List<T> newData) {
        this.data = newData;
        notifyDataSetChanged();
    }

    public List<T> getData() {
        return data;
    }

    public void setItemAction(ItemAction itemAction) {
        this.itemAction = itemAction;
    }
}
