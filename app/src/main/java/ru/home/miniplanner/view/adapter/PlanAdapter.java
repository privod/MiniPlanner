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

        TextView nameTextView = (TextView) view.findViewById(R.id.edit_text_date);
        TextView dateRegTextView = (TextView) view.findViewById(R.id.text_view_date_reg);
        TextView costTotalTextView = (TextView) view.findViewById(R.id.text_view_cost_total);

        getViewService().textViewSetText(nameTextView, plan.getName());
        getViewService().textViewSetText(dateRegTextView, plan.getDateReg());
        getViewService().textViewSetText(costTotalTextView, plan.getTotalCost());

        return view;
    }
}
