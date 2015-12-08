package ru.home.miniplanner.view.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ru.home.miniplanner.R;
import ru.home.miniplanner.model.Plan;
import ru.home.miniplanner.view.ViewService;

/**
 * Created by privod on 19.10.2015.
 */
public class PlanAdapter extends PlannerBaseAdapter<Plan> {
    static final String LOG_TAG = PlanAdapter.class.getSimpleName();

    Context context;

    public PlanAdapter(Context context, List<Plan> list) {
        super(context, list);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);

        if (null == view) {
            view = layout.inflate(R.layout.plan_view, parent, false);
        }

        ViewService viewService = new ViewService(context);
        Plan plan = (Plan) getItem(position);

        TextView nameTextView = (TextView) view.findViewById(R.id.nameTextView);
        TextView dateRegTextView = (TextView) view.findViewById(R.id.dateRegTextView);
        TextView costExpectTextView = (TextView) view.findViewById(R.id.costExpectTextView);
        TextView costTotalTextView = (TextView) view.findViewById(R.id.costTotalTextView);

        viewService.textViewSetText(nameTextView, plan.getName());
        viewService.textViewSetText(dateRegTextView, plan.getDateReg());
        viewService.textViewSetText(costExpectTextView, plan.getCostExpect());
        viewService.textViewSetText(costTotalTextView, plan.getTotalCost());

        return view;
    }
}
