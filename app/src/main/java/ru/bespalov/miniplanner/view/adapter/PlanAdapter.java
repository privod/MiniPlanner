package ru.bespalov.miniplanner.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.android.multiselector.MultiSelector;

import ru.bespalov.miniplanner.R;
import ru.bespalov.miniplanner.Util;
import ru.bespalov.miniplanner.model.Plan;

/**
 * Created by privod on 19.10.2015.
 */
public class PlanAdapter extends BaseAdapter<PlanAdapter.PlanViewHolder, Plan> {

    public PlanAdapter(MultiSelector multiSelector) {
        super(multiSelector/*, PlanViewHolder.class*/);
    }

    class PlanViewHolder extends BaseAdapter.ViewHolder {
        private TextView costTotalTextView;
        private TextView nameTextView;
        private TextView dateRegTextView;
        private ImageView avatarIcon;

        PlanViewHolder(View itemView) {
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.text_view_name);
            dateRegTextView = (TextView) itemView.findViewById(R.id.text_view_date_reg);
            costTotalTextView = (TextView) itemView.findViewById(R.id.text_view_total_cost);
            avatarIcon = (ImageView) itemView.findViewById(R.id.icon_avatar);
        }
    }

    @Override
    public PlanViewHolder onCreateViewHolder(ViewGroup parent, int viewType)  {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_plan, parent, false);

        return new PlanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlanViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);

        Plan plan = getData().get(position);
        holder.nameTextView.setText(plan.getName());
        holder.dateRegTextView.setText(Util.dateToString(plan.getDateReg()));
        holder.costTotalTextView.setText(plan.getTotalCost().toPlainString());
        holder.avatarIcon.setImageDrawable(newAvatarDrawable(plan.getName()));
    }
}