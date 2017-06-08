package ru.bespalov.miniplanner.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.android.multiselector.MultiSelector;

import ru.bespalov.miniplanner.R;
import ru.bespalov.miniplanner.Util;
import ru.bespalov.miniplanner.model.Bay;

/**
 * Created by privod on 19.10.2015.
 */

public class BayAdapter extends BaseAdapter<BayAdapter.BayViewHolder, Bay> {

    public BayAdapter(MultiSelector multiSelector) {
        super(multiSelector/*, PlanViewHolder.class*/);
    }

    class BayViewHolder extends BaseAdapter.ViewHolder {
        private TextView descriptionTextView;
        private TextView dateRegTextView;
        private TextView costTextView;
        //        private AvatarViewSwitcher avatarViewSwitcher;
        private ImageView avatarIcon;

        BayViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemAction.edit(getAdapterPosition());
                }
            });

            descriptionTextView = (TextView) itemView.findViewById(R.id.text_view_description);
            dateRegTextView = (TextView) itemView.findViewById(R.id.text_view_date_reg);
            costTextView = (TextView) itemView.findViewById(R.id.text_view_sum);
            avatarIcon = (ImageView) itemView.findViewById(R.id.icon_avatar);
        }
    }

    @Override
    public BayAdapter.BayViewHolder onCreateViewHolder(ViewGroup parent, int viewType)  {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_bay, parent, false);

        return new BayAdapter.BayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BayAdapter.BayViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);

        Bay contribution = getData().get(position);
        holder.descriptionTextView.setText(contribution.getDescription());
        holder.dateRegTextView.setText(Util.dateToString(contribution.getDateReg()));
        holder.costTextView.setText(contribution.getSum().toPlainString());
        holder.avatarIcon.setImageDrawable(newAvatarDrawable(contribution.getDescription()));
    }
}