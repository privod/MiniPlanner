package ru.home.miniplanner.view.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ru.home.miniplanner.R;
import ru.home.miniplanner.model.Party;
import ru.home.miniplanner.view.ViewService;

/**
 * Created by privod on 28.10.2015.
 */
public class PartyAdapter extends PlannerBaseAdapter<Party> {
    public PartyAdapter(Context context, List<Party> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);

        if (null == view) {
            view = layout.inflate(R.layout.party_view, parent, false);
        }

        ViewService viewService = new ViewService();
        Party party = (Party) getItem(position);

        TextView nameEditText = (TextView) view.findViewById(R.id.nameTextView);
//        TextView depositRegEditText = (TextView) view.findViewById(R.id.depositTextView);
//        TextView costExpectEditText = (TextView) view.findViewById(R.id.costTextView);

        viewService.textViewSetText(nameEditText, party.getName());
//        viewService.textViewSetText(depositRegEditText, party.getDeposit());
//        viewService.textViewSetText(costExpectEditText, plan.getCostExpect());

        return view;
    }
}
