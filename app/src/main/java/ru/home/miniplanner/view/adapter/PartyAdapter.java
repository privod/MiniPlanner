package ru.home.miniplanner.view.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.math.BigDecimal;

import ru.home.miniplanner.R;
import ru.home.miniplanner.model.Bay;
import ru.home.miniplanner.model.Party;

/**
 * Created by privod on 28.10.2015.
 */
public class PartyAdapter extends PlannerBaseAdapter<Party> {
    static final String LOG_TAG = PartyAdapter.class.getSimpleName();

//    public PartyAdapter(Context context, List<Party> list) {
//        super(context, list);
//    }

    public PartyAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);

        if (null == view) {
            view = getLayout().inflate(R.layout.party_view, parent, false);
        }

        final Party party = (Party) getItem(position);

        TextView nameEditText = (TextView) view.findViewById(R.id.partyTextView);
        TextView debtTextView = (TextView) view.findViewById(R.id.debtTextView);

        getViewService().textViewSetText(nameEditText, party.getName());
        getViewService().textViewSetText(debtTextView, party.getDebt().abs());

        if (party.getDebt().compareTo(new BigDecimal("0")) > 0) {
            debtTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.red));
        } else {
            debtTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.green));
        }

        StringBuilder bayNamesStr = new StringBuilder();
        StringBuilder bayCostsStr = new StringBuilder();
        for (Bay bay : party.getBays()) {
            bayNamesStr.append(bay.getDescription()).append("\n");
            bayCostsStr.append(bay.getCost()).append("\n");
        }
        TextView bayNameTextView = (TextView) view.findViewById(R.id.bayNameTextView);
        TextView bayCostTextView = (TextView) view.findViewById(R.id.bayCostTextView);
        getViewService().textViewSetText(bayNameTextView, bayNamesStr.toString());
        getViewService().textViewSetText(bayCostTextView, bayCostsStr.toString());
        if (bayCostsStr.toString().isEmpty()) {
//            ((LinearLayout) view.findViewById(R.id.titleLayout)).setVisibility(View.GONE);
            ((LinearLayout) view.findViewById(R.id.bodyLayout)).setVisibility(View.GONE);
        } else {
//            ((LinearLayout) view.findViewById(R.id.titleLayout)).setVisibility(View.VISIBLE);
            ((LinearLayout) view.findViewById(R.id.bodyLayout)).setVisibility(View.VISIBLE);
        }

        return view;
    }
}
