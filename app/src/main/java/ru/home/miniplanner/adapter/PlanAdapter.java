package ru.home.miniplanner.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import ru.home.miniplanner.R;
import ru.home.miniplanner.domain.Plan;

/**
 * Created by privod on 19.10.2015.
 */
public class PlanAdapter extends PlannerAdapter<Plan>{
    public PlanAdapter(Context context, List<Plan> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);

        Plan plan = (Plan) getItem(position);

        ((TextView) view.findViewById(R.id.dateRegTextView)).setText(plan.getDateReg().toString());
        ((TextView) view.findViewById(R.id.nameTextView)).setText(plan.getName());
        ((TextView) view.findViewById(R.id.costTextView)).setText(String.format("%.2fр.", plan.getCostExpect()));

        return view;
    }
}
