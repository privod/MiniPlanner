package ru.home.miniplanner.view.adapter;

import android.animation.StateListAnimator;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewAnimator;
import android.widget.ViewSwitcher;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.bignerdranch.android.multiselector.MultiSelector;

import ru.home.miniplanner.R;
import ru.home.miniplanner.Util;
import ru.home.miniplanner.model.Plan;
import ru.home.miniplanner.view.PlansActivity;

/**
 * Created by privod on 19.10.2015.
 */
public class PlanAdapter extends BaseAdapter<PlanAdapter.PlanViewHolder, Plan> {

//    private TextDrawable checkedDrawable;

    public PlanAdapter(Context context, MultiSelector multiSelector) {
//        super(PlanViewHolder.class);
        super(multiSelector, PlanViewHolder.class);

//        checkedDrawable = TextDrawable.builder().buildRound(" ", ContextCompat.getColor(context, R.color.material_gray_700));
    }

    public class PlanViewHolder extends BaseAdapter.ViewHolder {
        private TextView costTotalTextView;
        private TextView nameTextView;
        private TextView dateRegTextView;
        private ViewSwitcher avatarViewSwitcher;
//        private FrameLayout avatarLayout;
        private ImageView avatarIcon;
//        private ImageView checkedIcon;

//        private TextDrawable avatarDrawable;

        public PlanViewHolder(View itemView) {
            super(itemView);
            Context context = itemView.getContext();

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Log.d(LOG_TAG, "Layout Click");
                    if (v.getContext() instanceof PlansActivity) {
                        ((PlansActivity) v.getContext()).startPartiesActivity(getAdapterPosition());
                    }
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
//                    Log.d(LOG_TAG, "Layout LongClick");
                    if (v.getContext() instanceof PlansActivity) {
                        ((PlansActivity) v.getContext()).planSelect(v, PlanViewHolder.this);
                        return true;
                    }
//                    if (!multiSelector.isSelectable()) {
//                        multiSelector.setSelectable(true);
//                        multiSelector.setSelected(PlanViewHolder.this, true);
//                    }

                    return false;
                }
            });

            nameTextView = (TextView) itemView.findViewById(R.id.text_view_name);
            dateRegTextView = (TextView) itemView.findViewById(R.id.text_view_date_reg);
            costTotalTextView = (TextView) itemView.findViewById(R.id.text_view_cost_total);
//            avatarLayout = (FrameLayout) itemView.findViewById(R.id.frame_layout_avatar);
            avatarIcon = (ImageView) itemView.findViewById(R.id.icon_avatar);
//            checkedIcon = (ImageView) itemView.findViewById(R.id.icon_check);
//            checkedIcon.setImageDrawable(TextDrawable.builder().buildRound(" ", ContextCompat.getColor(context, R.color.material_gray_700)));
            avatarViewSwitcher = (ViewSwitcher) itemView.findViewById(R.id.view_swicher_avatar);
            avatarViewSwitcher.setInAnimation(context, R.anim.avatar_in);
            avatarViewSwitcher.setOutAnimation(context, R.anim.avatar_out);
        }

        // TODO Есть сомнения в технолигичности решения
        public TextDrawable getNewAvatarDrawable(String text) {
            String letter = String.valueOf(Character.toUpperCase(text.charAt(0)));
            ColorGenerator generator = ColorGenerator.MATERIAL;
//            avatarDrawable = TextDrawable.builder().buildRound(letter, generator.getColor(letter));
//
//            return avatarDrawable;
            return TextDrawable.builder().buildRound(letter, generator.getColor(letter));
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

//        if (multiSelector.isSelected(position, holder.getItemId())) {
//            holder.avatarIcon.setImageDrawable(TextDrawable.builder().buildRound(" ", ContextCompat.getColor(holder.itemView.getContext(), R.color.material_gray_700)));
//            holder.checkedIcon.setVisibility(View.VISIBLE);
//            holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.material_gray_300));
//        } else {
//            holder.avatarIcon.setImageDrawable(holder.getNewAvatarDrawable(plan.getName()));
//            holder.checkedIcon.setVisibility(View.GONE);
//            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
//        }


        /*if (!holder.nameTextView.getText().equals(plan.getName())) {
            holder.nameTextView.setText(plan.getName());
            holder.avatarIcon.setImageDrawable(holder.getNewAvatarDrawable(plan.getName()));
        }

        if (multiSelector.isSelected(position, holder.getItemId()) != (holder.avatarViewSwitcher.getDisplayedChild() == 1)) {
            holder.avatarViewSwitcher.showNext();
        }

//        boolean newChecked = ((ListView) parent).isItemChecked(position);
//        if (newChecked != getArrayChecked().get(position)) {
//            if (newChecked) {
//
//            } else {
//
//            }
//
//        }
        if (multiSelector.isSelected(position, holder.getItemId())) {
            if (holder.avatarIcon.getDrawable() != checkedDrawable) {
                // TODO Вынести в метод
                holder.avatarIcon.setImageDrawable(checkedDrawable);
                holder.checkedIcon.setVisibility(View.VISIBLE);
                holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.material_gray_400));
            }
        } else {
            if (holder.avatarIcon.getDrawable() != holder.avatarDrawable) {
                // TODO Вынести в тот же метод
                holder.avatarIcon.setImageDrawable(holder.avatarDrawable);
                holder.checkedIcon.setVisibility(View.GONE);
                holder.itemView.setBackgroundColor(Color.TRANSPARENT);
            }
        }*/

        holder.dateRegTextView.setText(Util.dateToString(plan.getDateReg()));
        holder.costTotalTextView.setText(plan.getTotalCost().toPlainString());
    }
}