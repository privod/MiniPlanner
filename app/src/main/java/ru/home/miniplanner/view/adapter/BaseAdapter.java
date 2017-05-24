package ru.home.miniplanner.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.bignerdranch.android.multiselector.MultiSelector;
import com.bignerdranch.android.multiselector.SwappingHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ru.home.miniplanner.R;
import ru.home.miniplanner.model.Domain;
import ru.home.miniplanner.view.widget.AvatarViewSwitcher;

/**
 * Created by privod on 19.10.2015.
 */
public abstract class BaseAdapter<VH extends BaseAdapter.ViewHolder, T extends Domain> extends RecyclerView.Adapter<VH> {

    private List<T> data;

    protected MultiSelector multiSelector;
    private ItemAction itemAction;

    BaseAdapter(MultiSelector multiSelector) {
        this.multiSelector = multiSelector;
        this.data = new ArrayList<T>();
    }

    public static abstract class ItemAction {

        public abstract void open(int position);

        public abstract void selectSwitch(BaseAdapter.ViewHolder holder);
    }

    public abstract class ViewHolder extends SwappingHolder {
        AvatarViewSwitcher avatarViewSwitcher;

        ViewHolder(final View itemView) {
            super(itemView, multiSelector);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    if (v.getContext() instanceof BaseListActivity) {
//                        ((BaseListActivity) v.getContext()).listItemOpen(getAdapterPosition());
//                    }
                    itemAction.open(getAdapterPosition());
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
//                    if (v.getContext() instanceof BaseListActivity) {
//                        ((BaseListActivity) v.getContext()).selectSwitch(ViewHolder.this);
//                        return true;
//                    }
                    itemAction.selectSwitch(ViewHolder.this);

                    return true;
                }
            });
            avatarViewSwitcher = (AvatarViewSwitcher) itemView.findViewById(R.id.view_switcher_avatar);
            avatarViewSwitcher.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    if (v.getContext() instanceof BaseListActivity) {
//                        ((BaseListActivity) v.getContext()).selectSwitch(ViewHolder.this);
//                    }
                    itemAction.selectSwitch(ViewHolder.this);
                }
            });
        }
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {

        if (multiSelector.isSelected(position, holder.getItemId())) {
            holder.avatarViewSwitcher.setDisplayedChildNoAnim(1);
//            holder.itemView.setBackgroundColor(Color.LTGRAY);
        } else {
            holder.avatarViewSwitcher.setDisplayedChildNoAnim(0);
//            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
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

    /*public int getPositionById(long id) {
        for (int AdapterPosition = 0; AdapterPosition < this.data.size(); AdapterPosition++) {
            if (this.data.get(AdapterPosition).getId() == id) {
                return AdapterPosition;
            }
        }

        return -1;
    }   // */

    public void updateData(List<T> newData) {
        this.data = newData;
        notifyDataSetChanged();

//        Set<Integer> updatedPosition = new HashSet<>();
        /*for (int AdapterPosition = 0; AdapterPosition < this.data.size(); AdapterPosition++) {
            T item = this.data.get(AdapterPosition);
            int positionNew = Util.positionById(newData, item.getId());
            if (positionNew < 0) {
                this.data.remove(item);
                notifyItemRemoved(AdapterPosition);
            } else {
                this.data.set(AdapterPosition, newData.get(positionNew));
                notifyItemChanged(AdapterPosition);
            }
            updatedPosition.add(positionNew);
        }

        for (int AdapterPosition = 0; AdapterPosition < newData.size(); AdapterPosition++) {
            if (!updatedPosition.contains(AdapterPosition)) {
                this.data.add(newData.get(AdapterPosition));
                notifyItemInserted(AdapterPosition);
            }
        } // */

        /*for (int i = 0; i < newData.size(); i++) {
            T itemNew = newData.get(i);
            int AdapterPosition = getPositionById(itemNew.getId());
            if (AdapterPosition < 0) {
                this.data.add(itemNew);
                AdapterPosition = this.data.indexOf(itemNew);
                notifyItemInserted(AdapterPosition);
            } else {
                this.data.set(AdapterPosition, itemNew);
                notifyItemChanged(AdapterPosition);
            }
            updatedPosition.add(AdapterPosition);
        } // */
    }

    public void updateData(Collection<T> c) {
        this.updateData(new ArrayList<>(c));
    }

    public List<T> getData() {
        return data;
    }

    public void setItemAction(ItemAction itemAction) {
        this.itemAction = itemAction;
    }
}
