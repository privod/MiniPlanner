package ru.home.miniplanner.view.adapter;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.bignerdranch.android.multiselector.MultiSelector;

import ru.home.miniplanner.R;
import ru.home.miniplanner.Util;
import ru.home.miniplanner.model.Plan;
import ru.home.miniplanner.view.PlansActivity;
import ru.home.miniplanner.view.widget.AvatarViewSwitcher;

/**
 * Created by privod on 19.10.2015.
 */
public class PlanAdapter extends BaseAdapter<PlanAdapter.PlanViewHolder, Plan> {

    public PlanAdapter(MultiSelector multiSelector) {
        super(multiSelector, PlanViewHolder.class);
    }

    public class PlanViewHolder extends BaseAdapter.ViewHolder {
        private TextView costTotalTextView;
        private TextView nameTextView;
        private TextView dateRegTextView;
        private AvatarViewSwitcher avatarViewSwitcher;
        private ImageView avatarIcon;

        public PlanViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v.getContext() instanceof PlansActivity) {
                        ((PlansActivity) v.getContext()).startPartiesActivity(getAdapterPosition());
                    }
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (v.getContext() instanceof PlansActivity) {
                        ((PlansActivity) v.getContext()).selectSwitch(PlanViewHolder.this);
                        return true;
                    }

                    return false;
                }
            });

            nameTextView = (TextView) itemView.findViewById(R.id.text_view_name);
            dateRegTextView = (TextView) itemView.findViewById(R.id.text_view_date_reg);
            costTotalTextView = (TextView) itemView.findViewById(R.id.text_view_cost_total);
            avatarIcon = (ImageView) itemView.findViewById(R.id.icon_avatar);
            avatarViewSwitcher = (AvatarViewSwitcher) itemView.findViewById(R.id.view_switcher_avatar);
            avatarViewSwitcher.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v.getContext() instanceof PlansActivity) {
                        ((PlansActivity) v.getContext()).selectSwitch(PlanViewHolder.this);
                    }
                }
            });
//            setSelectionModeBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(itemView.getContext(), R.color.material_gray_300)));
//            setDefaultModeBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    @Override
    public PlanViewHolder onCreateViewHolder(ViewGroup parent, int viewType)  {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plan_view, parent, false);

        return new PlanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlanViewHolder holder, int position) {

        Plan plan = getData().get(position);
        holder.nameTextView.setText(plan.getName());
        holder.dateRegTextView.setText(Util.dateToString(plan.getDateReg()));
        holder.costTotalTextView.setText(plan.getTotalCost().toPlainString());
        holder.avatarIcon.setImageDrawable(newAvatarDrawable(plan.getName()));

        if (multiSelector.isSelected(position, holder.getItemId())) {
            holder.avatarViewSwitcher.setDisplayedChildNoAnim(1);
//            holder.itemView.setBackgroundColor(Color.LTGRAY);
        } else {
            holder.avatarViewSwitcher.setDisplayedChildNoAnim(0);
//            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    private TextDrawable newAvatarDrawable(String text) {
        String letter = String.valueOf(Character.toUpperCase(text.charAt(0)));
        return TextDrawable.builder().buildRound(letter, ColorGenerator.MATERIAL.getColor(letter));
    }
}