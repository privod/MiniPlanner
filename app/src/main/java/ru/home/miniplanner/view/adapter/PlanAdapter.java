package ru.home.miniplanner.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.android.multiselector.MultiSelector;

import ru.home.miniplanner.R;
import ru.home.miniplanner.Util;
import ru.home.miniplanner.model.Plan;

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
//        private AvatarViewSwitcher avatarViewSwitcher;
        private ImageView avatarIcon;

        PlanViewHolder(View itemView) {
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.text_view_name);
            dateRegTextView = (TextView) itemView.findViewById(R.id.text_view_date_reg);
            costTotalTextView = (TextView) itemView.findViewById(R.id.text_view_total_cost);
            avatarIcon = (ImageView) itemView.findViewById(R.id.icon_avatar);
//            avatarViewSwitcher = (AvatarViewSwitcher) itemView.findViewById(R.id.view_switcher_avatar);
//            avatarViewSwitcher.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (v.getContext() instanceof PlansActivity) {
//                        ((PlansActivity) v.getContext()).selectSwitch(PlanViewHolder.this);
//                    }
//                }
//            });
//            setSelectionModeBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(itemView.getContext(), R.color.material_gray_300)));
//            setDefaultModeBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
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

//        if (multiSelector.isSelected(position, holder.getItemId())) {
//            holder.avatarViewSwitcher.setDisplayedChildNoAnim(1);
////            holder.itemView.setBackgroundColor(Color.LTGRAY);
//        } else {
//            holder.avatarViewSwitcher.setDisplayedChildNoAnim(0);
////            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
//        }
    }

//    private TextDrawable newAvatarDrawable(String text) {
//        String letter = String.valueOf(Character.toUpperCase(text.charAt(0)));
//        return TextDrawable.builder().buildRound(letter, ColorGenerator.MATERIAL.getColor(letter));
//    }
}