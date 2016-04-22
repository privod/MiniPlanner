package ru.home.miniplanner.view.RVAdater;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ru.home.miniplanner.R;
import ru.home.miniplanner.model.Plan;
import ru.home.miniplanner.view.PlansActivity;
import ru.home.miniplanner.view.ViewService;

/**
 * Created by bespalov on 27.02.16.
 */
public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.PlanViewHolder> {
    private static final String LOG_TAG = PlanAdapter.class.getSimpleName();
    private final ViewService viewService;
    private final View.OnCreateContextMenuListener menuListener;
    private int position;
    private List<Plan> plans;

    public PlanAdapter(View.OnCreateContextMenuListener menuListener) {
        this.menuListener = menuListener;
        viewService = new ViewService();
    }

    public static class PlanViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView costTotalTextView;
        private TextView nameTextView;
        private TextView dateRegTextView;
        private TextView mAvatarImage;

        public PlanViewHolder(View itemView, View.OnCreateContextMenuListener menuListener) {
            super(itemView);

            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(menuListener);
            nameTextView = (TextView) itemView.findViewById(R.id.nameTextView);
            dateRegTextView = (TextView) itemView.findViewById(R.id.dateRegTextView);
            costTotalTextView = (TextView) itemView.findViewById(R.id.costTotalTextView);
            mAvatarImage = (TextView) itemView.findViewById(R.id.avatarTextView);
//            mAvatarImage.getBackground();
        }

        @Override
        public void onClick(View view) {
            PlansActivity activity = (PlansActivity) view.getContext();
            Plan plan = activity.getAllPlans().get(getAdapterPosition());
            activity.openPartiesActivity(plan);
        }
    }

    @Override
    public PlanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plan_view, parent, false);

        return new PlanViewHolder(view, menuListener);
    }

    @Override
    public void onBindViewHolder(PlanViewHolder holder, int position) {
        Plan plan = plans.get(position);
        viewService.textViewSetText(holder.nameTextView, plan.getName());
        viewService.textViewSetText(holder.dateRegTextView, plan.getDateReg());
        viewService.textViewSetText(holder.costTotalTextView, plan.getTotalCost());
        viewService.textViewSetText(holder.mAvatarImage, plan.getName().substring(0,1).toUpperCase());
        Drawable drawable = holder.mAvatarImage.getBackground();
        if (drawable instanceof GradientDrawable)        {
            ((GradientDrawable) drawable).setColor(Color.BLUE);
        }
    }

    @Override
    public int getItemCount() {
        return plans.size();
    }

    public void setPlans(List<Plan> plans) {
        this.plans = plans;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
