package ru.bespalov.miniplanner.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.android.multiselector.MultiSelector;

import ru.bespalov.miniplanner.R;
import ru.bespalov.miniplanner.Util;
import ru.bespalov.miniplanner.model.Contribution;

/**
 * Created by privod on 19.10.2015.
 */
public class ContributionAdapter extends BaseAdapter<ContributionAdapter.ContributionViewHolder, Contribution> {

    public ContributionAdapter(MultiSelector multiSelector) {
        super(multiSelector/*, PlanViewHolder.class*/);
    }

    class ContributionViewHolder extends BaseAdapter.ViewHolder {
        private TextView descriptionTextView;
        private TextView dateRegTextView;
        private TextView sumTextView;
        private ImageView avatarIcon;

        ContributionViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemAction.edit(getAdapterPosition());
                }
            });

            descriptionTextView = (TextView) itemView.findViewById(R.id.text_view_description);
            dateRegTextView = (TextView) itemView.findViewById(R.id.text_view_date_reg);
            sumTextView = (TextView) itemView.findViewById(R.id.text_view_sum);
            avatarIcon = (ImageView) itemView.findViewById(R.id.icon_avatar);
        }
    }

    @Override
    public ContributionAdapter.ContributionViewHolder onCreateViewHolder(ViewGroup parent, int viewType)  {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_contribution, parent, false);

        return new ContributionAdapter.ContributionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContributionAdapter.ContributionViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);

        Contribution contribution = getData().get(position);
        holder.descriptionTextView.setText(contribution.getDescription());
        holder.dateRegTextView.setText(Util.dateToString(contribution.getDateReg()));
        holder.sumTextView.setText(contribution.getSumView());
        holder.avatarIcon.setImageDrawable(newAvatarDrawable(contribution.getDescription()));
    }
}
