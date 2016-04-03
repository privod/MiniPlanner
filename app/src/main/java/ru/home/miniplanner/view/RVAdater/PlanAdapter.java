package ru.home.miniplanner.view.RVAdater;

import android.app.Activity;
import android.content.Intent;
//import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ru.home.miniplanner.R;
import ru.home.miniplanner.model.Plan;
import ru.home.miniplanner.view.PartiesActivity;
import ru.home.miniplanner.view.PlansActivity;
import ru.home.miniplanner.view.ViewService;

/**
 * Created by bespalov on 27.02.16.
 */
public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.PlanViewHolder> {
    private static final String LOG_TAG = PlanAdapter.class.getSimpleName();
    private final ViewService viewService;
//    private final View.OnClickListener clickListener;
    private final View.OnCreateContextMenuListener menuListener;
    private int position;
//    ContextMenu.ContextMenuInfo info;
    private List<Plan> plans;


    public PlanAdapter(/*View.OnClickListener clickListener,*/ View.OnCreateContextMenuListener menuListener) {
//        this.clickListener = clickListener;
        this.menuListener = menuListener;
        viewService = new ViewService();
    }

    public static class PlanViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//            private View.OnClickListener clickListener;
            private View.OnCreateContextMenuListener menuListener;
            private List<Plan> plans;
            private TextView costTotalTextView;
            private TextView nameTextView;
            private TextView dateRegTextView;

        public PlanViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(menuListener);
            nameTextView = (TextView) itemView.findViewById(R.id.nameTextView);
            dateRegTextView = (TextView) itemView.findViewById(R.id.dateRegTextView);
            costTotalTextView = (TextView) itemView.findViewById(R.id.costTotalTextView);

        }

        @Override
        public void onClick(View view) {
            Log.e(LOG_TAG, "OnClickListener called on CardView number " + getPosition());

            Plan plan = plans.get(getPosition());
            Intent intent = new Intent(view.getContext(), PartiesActivity.class);
            intent.putExtra(Plan.EXTRA_NAME, plan);
            ((Activity) view.getContext()).startActivityForResult(intent, PlansActivity.REQUEST_PARTIES);
        }

//        public void setClickListener(View.OnClickListener clickListener) {
//            this.clickListener = clickListener;
//        }

        public void setMenuListener(View.OnCreateContextMenuListener menuListener) {
            this.menuListener = menuListener;
        }

        public void setPlans(List<Plan> plans) {
            this.plans = plans;
        }
    }

    @Override
    public PlanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plan_view, parent, false);
//        CardView cardView = (CardView) view.findViewById(R.id.card_view);
//
//        cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.e(LOG_TAG, "OnClickListener called!");
//                int position = parent.getChildPosition(view);
//                Plan plan = plans.get(position);
//                Intent intent = new Intent(view.getContext(), PartiesActivity.class);
//                intent.putExtra(Plan.EXTRA_NAME, plan);
//                view.startActivityForResult(intent, REQUEST_PARTIES);
//            }
//        });

        return new PlanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlanViewHolder holder, int position) {
        Plan plan = plans.get(position);
//        holder.setClickListener(clickListener);
        holder.setMenuListener(menuListener);
        holder.setPlans(plans);
        viewService.textViewSetText(holder.nameTextView, plan.getName());
        viewService.textViewSetText(holder.dateRegTextView, plan.getDateReg());
        viewService.textViewSetText(holder.costTotalTextView, plan.getTotalCost());
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

    //    public void setInfo(ContextMenu.ContextMenuInfo info) {
//        this.info = info;
//    }
}
