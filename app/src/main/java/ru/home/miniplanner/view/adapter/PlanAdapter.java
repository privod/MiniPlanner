package ru.home.miniplanner.view.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.home.miniplanner.R;
import ru.home.miniplanner.model.Plan;

/**
 * Created by privod on 19.10.2015.
 */
public class PlanAdapter extends PlannerBaseAdapter<Plan> {
    static final String LOG_TAG = PlanAdapter.class.getSimpleName();

    public PlanAdapter(Context context) {
        super(context);
    }

//    public PlanAdapter(Context context, List<Plan> list) {
//        super(context, list);
//    }
//
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);

        if (null == view) {
            view = getLayout().inflate(R.layout.plan_view, parent, false);
        }

        Plan plan = (Plan) getItem(position);

        TextView nameTextView = (TextView) view.findViewById(R.id.descriptionTextView);
        TextView dateRegTextView = (TextView) view.findViewById(R.id.dateRegTextView);
//        TextView costExpectTextView = (TextView) view.findViewById(R.id.costExpectTextView);
        TextView costTotalTextView = (TextView) view.findViewById(R.id.costTotalTextView);

        getViewService().textViewSetText(nameTextView, plan.getName());
        getViewService().textViewSetText(dateRegTextView, plan.getDateReg());
//        viewService.textViewSetText(costExpectTextView, plan.getCostExpect());
        getViewService().textViewSetText(costTotalTextView, plan.getTotalCost());

        return view;
    }
}
