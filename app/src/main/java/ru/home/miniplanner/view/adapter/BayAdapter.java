package ru.home.miniplanner.view.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.home.miniplanner.R;
import ru.home.miniplanner.model.Bay;

/**
 * Created by privod on 19.10.2015.
 */
public class BayAdapter extends BaseAdapter<Bay> {
    static final String LOG_TAG = BayAdapter.class.getSimpleName();

    public BayAdapter(Context context) {
        super(context);
    }

//    public BayAdapter(Context context, List<Bay> list) {
//        super(context, list);
//    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);

        if (null == view) {
            view = getLayout().inflate(R.layout.bay_view, parent, false);
        }

        Bay bay = (Bay) getItem(position);

        TextView descriptionTextView = (TextView) view.findViewById(R.id.descriptionTextView);
        TextView dateRegTextView = (TextView) view.findViewById(R.id.text_view_date_reg);
        TextView costTextView = (TextView) view.findViewById(R.id.costTextView);

        getViewService().textViewSetText(descriptionTextView, bay.getDescription());
        getViewService().textViewSetText(dateRegTextView, bay.getDateReg());
//        viewService.textViewSetText(costExpectTextView, plan.getCostExpect());
        getViewService().textViewSetText(costTextView, bay.getCost());

        return view;
    }
}
