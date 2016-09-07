package ru.home.miniplanner.view.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import ru.home.miniplanner.R;
import ru.home.miniplanner.model.Plan;

/**
 * Created by privod on 19.10.2015.
 */
public class PlanAdapter extends BaseAdapter<PlanAdapter.PlanViewHolder, Plan> {

    private TextDrawable checkedDrawable;

    public PlanAdapter(Context context) {
        super(PlanViewHolder.class);

        checkedDrawable = TextDrawable.builder().buildRound(" ", ContextCompat.getColor(context, R.color.material_gray_700));
    }

    public class PlanViewHolder extends BaseAdapter.ViewHolder {
        private TextView costTotalTextView;
        private TextView nameTextView;
        private TextView dateRegTextView;
        private FrameLayout avatarLayout;
        private ImageView avatarIcon;
        private ImageView checkedIcon;

//        private TextDrawable avatarDrawable;

        public PlanViewHolder(View itemView) {
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.text_view_name);
            dateRegTextView = (TextView) itemView.findViewById(R.id.text_view_date_reg);
            costTotalTextView = (TextView) itemView.findViewById(R.id.text_view_cost_total);
            avatarLayout = (FrameLayout) itemView.findViewById(R.id.frame_layout_avatar);
            avatarIcon = (ImageView) itemView.findViewById(R.id.icon_avatar);
            checkedIcon = (ImageView) itemView.findViewById(R.id.icon_check);
        }

        // TODO Есть сомнения в технолигичности решения
        public TextDrawable getNewAvatarDrawable(String text) {
            String letter = String.valueOf(Character.toUpperCase(text.charAt(0)));
            ColorGenerator generator = ColorGenerator.MATERIAL;
            TextDrawable avatarDrawable = TextDrawable.builder().buildRound(letter, generator.getColor(letter));

            return avatarDrawable;
        }
    }

    @Override
    public PlanViewHolder onCreateViewHolder(ViewGroup parent) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plan_view, parent, false);

        return new PlanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlanViewHolder holder, int position, ViewGroup parent) {

        Plan plan = getItem(position);
        if (!holder.nameTextView.getText().equals(plan.getName())) {
            holder.nameTextView.setText(plan.getName());
            holder.avatarIcon.setImageDrawable(holder.getNewAvatarDrawable(plan.getName()));
        }

        boolean newChecked = ((ListView) parent).isItemChecked(position);
        if (newChecked != getArrayChecked().get(position)) {
            if (newChecked) {

            } else {

            }

        }
        /*if (getArrayChecked().get(position, false)) {
            if (holder.avatarIcon.getDrawable() != holder.checkedDrawable) {
                // TODO Вынести в метод
                holder.avatarIcon.setImageDrawable(holder.checkedDrawable);
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