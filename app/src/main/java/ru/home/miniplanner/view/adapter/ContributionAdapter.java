package ru.home.miniplanner.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.android.multiselector.MultiSelector;

import ru.home.miniplanner.R;
import ru.home.miniplanner.Util;
import ru.home.miniplanner.model.Contribution;
import ru.home.miniplanner.model.Plan;

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
//        private AvatarViewSwitcher avatarViewSwitcher;
        private ImageView avatarIcon;

        ContributionViewHolder(View itemView) {
            super(itemView);

            descriptionTextView = (TextView) itemView.findViewById(R.id.text_view_description);
            dateRegTextView = (TextView) itemView.findViewById(R.id.text_view_date_reg);
            sumTextView = (TextView) itemView.findViewById(R.id.text_view_sum);
            avatarIcon = (ImageView) itemView.findViewById(R.id.icon_avatar);
        }
    }

    @Override
    public ContributionAdapter.ContributionViewHolder onCreateViewHolder(ViewGroup parent, int viewType)  {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plan_view, parent, false);

        return new ContributionAdapter.ContributionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContributionAdapter.ContributionViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);

        Contribution contribution = getData().get(position);
        holder.descriptionTextView.setText(contribution.getDescription());
        holder.dateRegTextView.setText(Util.dateToString(contribution.getDateReg()));
        holder.sumTextView.setText(contribution.getSum().toPlainString());
        holder.avatarIcon.setImageDrawable(newAvatarDrawable(contribution.getDescription()));
    }
}
