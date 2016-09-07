package ru.home.miniplanner.view.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.home.miniplanner.R;
import ru.home.miniplanner.model.Contribution;

/**
 * Created by privod on 19.10.2015.
 */
public class ContributionAdapter extends OldBaseAdapter<Contribution> {
    static final String LOG_TAG = ContributionAdapter.class.getSimpleName();

    public ContributionAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);

        if (null == view) {
            view = getLayout().inflate(R.layout.contribution_view, parent, false);
        }

        Contribution contribution = (Contribution) getItem(position);

//        TextView descriptionTextView = (TextView) view.findViewById(R.id.descriptionTextView);
        TextView partyTextView = (TextView) view.findViewById(R.id.descriptionTextView);
        TextView dateRegTextView = (TextView) view.findViewById(R.id.text_view_date_reg);
        TextView sumTextView = (TextView) view.findViewById(R.id.sumTextView);

//        getViewService().textViewSetText(descriptionTextView, contribution.getDescription());
        getViewService().textViewSetText(partyTextView, contribution.getTo().getName());
        getViewService().textViewSetText(dateRegTextView, contribution.getDateReg());
        getViewService().textViewSetText(sumTextView, contribution.getSum());

        return view;
    }
}
