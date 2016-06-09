package ru.home.miniplanner.view.RVAdater;

import android.graphics.Color;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bignerdranch.android.multiselector.ModalMultiSelectorCallback;
import com.bignerdranch.android.multiselector.MultiSelector;
import com.bignerdranch.android.multiselector.SwappingHolder;

import java.util.List;

import ru.home.miniplanner.R;
import ru.home.miniplanner.model.Plan;
import ru.home.miniplanner.view.PlansActivity;
import ru.home.miniplanner.view.ViewService;
import ru.home.miniplanner.view.widget.AvatarLetterView;

/**
 * Created by bespalov on 27.02.16.
 */
public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.PlanViewHolder> {
    private static final String LOG_TAG = PlanAdapter.class.getSimpleName();
    private final ViewService viewService;
    private int position;
    private List<Plan> plans;

    private MultiSelector multiSelector;

    public PlanAdapter(MultiSelector multiSelector) {
        this.multiSelector = multiSelector;
        viewService = new ViewService();
    }

    public class PlanViewHolder extends SwappingHolder  /*implements View.OnClickListener*/ {
        private TextView costTotalTextView;
        private TextView nameTextView;
        private TextView dateRegTextView;
        private AvatarLetterView avatarLetter;

        public PlanViewHolder(final View itemView) {
            super(itemView, multiSelector);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Log.d(LOG_TAG, "Layout Click");
                    if (v.getContext() instanceof PlansActivity) {
                        ((PlansActivity) v.getContext()).openPartiesActivity(getAdapterPosition());
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

            nameTextView = (TextView) itemView.findViewById(R.id.nameTextView);
            dateRegTextView = (TextView) itemView.findViewById(R.id.dateRegTextView);
            costTotalTextView = (TextView) itemView.findViewById(R.id.costTotalTextView);

            avatarLetter = (AvatarLetterView) itemView.findViewById(R.id.avatarLetter);
            avatarLetter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Log.d(LOG_TAG, "Avatar Click");
                    if (v.getContext() instanceof PlansActivity) {
                        ((PlansActivity) v.getContext()).planSelect(v, PlanViewHolder.this);
                    }
//                    if (!multiSelector.isSelectable()) {
//                        multiSelector.setSelectable(true);
//                        multiSelector.setSelected(PlanViewHolder.this, true);
//                    }
                }
            });
        }
    }

    @Override
    public PlanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plan_view, parent, false);

        return new PlanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlanViewHolder holder, int position) {
        Plan plan = plans.get(position);
        viewService.textViewSetText(holder.nameTextView, plan.getName());
        viewService.textViewSetText(holder.dateRegTextView, plan.getDateReg());
        viewService.textViewSetText(holder.costTotalTextView, plan.getTotalCost());
        holder.avatarLetter.setLetter(plan.getName());
        holder.avatarLetter.setSelectedState(plan.isSelected());

//        holder.avatarLetter.getAvatarDrawable().setColor(plan.getColor());
        holder.avatarLetter.getAvatarDrawable().setColor(Color.BLUE);
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
