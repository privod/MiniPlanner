package ru.home.miniplanner.view.adapter;

import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.List;

import ru.home.miniplanner.R;
import ru.home.miniplanner.model.Bay;
import ru.home.miniplanner.model.Party;
import ru.home.miniplanner.view.PartiesActivity;
import ru.home.miniplanner.view.ViewService;

/**
 * Created by privod on 28.10.2015.
 */
public class PartyAdapter extends PlannerBaseAdapter<Party> {
    static final String LOG_TAG = PartyAdapter.class.getSimpleName();

    Context context;
    PartiesActivity activity;

    public PartyAdapter(Context context, List<Party> list) {
        super(context, list);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);

        if (null == view) {
            view = layout.inflate(R.layout.party_view, parent, false);
        }

        if (context instanceof PartiesActivity) {
            activity = (PartiesActivity)context;
        }
        else {
            Log.e(LOG_TAG, "context must instance of PartiesActivity");
        }

        ViewService viewService = new ViewService(context);
        final Party party = (Party) getItem(position);

        TextView nameEditText = (TextView) view.findViewById(R.id.nameTextView);
        TextView debtTextView = (TextView) view.findViewById(R.id.debtTextView);
        viewService.textViewSetText(nameEditText, party.getName());
        BigDecimal debt = party.getDebt();
        int redColor = context.getResources().getColor(R.color.red);
        int greenColor = context.getResources().getColor(R.color.green);
        if (debt.compareTo(new BigDecimal("0")) > 0) {
            debtTextView.setTextColor(redColor);
        } else {
            debtTextView.setTextColor(greenColor);
        }
        viewService.textViewSetText(debtTextView, party.getDebt().abs());

        StringBuilder bayNamesStr = new StringBuilder();
        StringBuilder bayCostsStr = new StringBuilder();
        for (Bay bay : party.getBays()) {
            bayNamesStr.append(bay.getDescription()).append("\n");
            bayCostsStr.append(bay.getCost()).append("\n");
        }
        TextView bayNameTextView = (TextView) view.findViewById(R.id.bayNameTextView);
        TextView bayCostTextView = (TextView) view.findViewById(R.id.bayCostTextView);
        viewService.textViewSetText(bayNameTextView, bayNamesStr.toString());
        viewService.textViewSetText(bayCostTextView, bayCostsStr.toString());
        if (bayCostsStr.toString().isEmpty()) {
            ((LinearLayout) view.findViewById(R.id.titleLayout)).setVisibility(View.GONE);
            ((LinearLayout) view.findViewById(R.id.bodyLayout)).setVisibility(View.GONE);
        } else {
            ((LinearLayout) view.findViewById(R.id.titleLayout)).setVisibility(View.VISIBLE);
            ((LinearLayout) view.findViewById(R.id.bodyLayout)).setVisibility(View.VISIBLE);
        }

//        ImageButton contributionButton = (ImageButton) view.findViewById(R.id.contributionButton);
//        ImageButton bayButton = (ImageButton) view.findViewById(R.id.bayButton);
//        ImageButton popupButton = (ImageButton) view.findViewById(R.id.popupButton);
//        contributionButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        });
//        bayButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Bay bay = new Bay();
//                bay.setParty(party);
//                 activity.openBayEditActivity(bay);
//            }
//        });
//        popupButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
//                popupMenu.inflate(R.menu.context_party);
//                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                    @Override
//                    public boolean onMenuItemClick(MenuItem item) {
//                        long id = item.getItemId();
//                        if (id == R.id.context_party_edit) {
//                            activity.openPartyEditActivity(party);
//                            return true;
//                        } else if (id == R.id.context_party_del) {
//                            activity.partyDelete(party);
//                            return true;
//                        }
//                        return false;
//                    }
//                });
//                popupMenu.show();
//            }
//        });

        return view;
    }
}
