package ru.home.miniplanner.view.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import ru.home.miniplanner.PlansActivity;
import ru.home.miniplanner.R;
import ru.home.miniplanner.model.Plan;
import ru.home.miniplanner.view.ViewService;

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

        ViewService viewService = new ViewService();
        Plan plan = (Plan) getItem(position);

        TextView nameEditText = (TextView) view.findViewById(R.id.nameTextView);
        TextView dateRegEditText = (TextView) view.findViewById(R.id.dateRegTextView);
        TextView costExpectEditText = (TextView) view.findViewById(R.id.costTextView);

        viewService.textViewSetText(nameEditText, plan.getName());
        viewService.textViewSetText(dateRegEditText, plan.getDateReg());
        viewService.textViewSetText(costExpectEditText, plan.getCostExpect());

        return view;
    }
}
