package ru.home.miniplanner.view.adapter;

import android.content.Context;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.List;

import ru.home.miniplanner.R;
import ru.home.miniplanner.model.Party;
import ru.home.miniplanner.view.PartiesActivity;
import ru.home.miniplanner.view.ViewService;

/**
 * Created by privod on 28.10.2015.
 */
public class PartyAdapter extends PlannerBaseAdapter<Party> {
    static final String LOG_TAG = PartyAdapter.class.getSimpleName();

    Context context;

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

        ViewService viewService = new ViewService(context);
        final Party party = (Party) getItem(position);

        TextView nameEditText = (TextView) view.findViewById(R.id.nameTextView);
        TextView debtTextView = (TextView) view.findViewById(R.id.debtTextView);
        ImageButton contributionButton = (ImageButton) view.findViewById(R.id.contributionButton);
        ImageButton bayButton = (ImageButton) view.findViewById(R.id.bayButton);
        ImageButton popupButton = (ImageButton) view.findViewById(R.id.popupButton);

        viewService.textViewSetText(nameEditText, party.getName());
        viewService.textViewSetMoney(debtTextView, party.getBaysTotalCost());
        contributionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        bayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        popupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
                popupMenu.inflate(R.menu.context_party);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        PartiesActivity activity;
                        if (context instanceof PartiesActivity) {
                            activity = (PartiesActivity)context;
                        }
                        else {
                            Log.e(LOG_TAG, "context must instance of PartiesActivity");
                            return false;
                        }

                        long id = item.getItemId();
                        if (id == R.id.context_party_edit) {
                            activity.openPartyEditActivity(party);
                            return true;
                        } else if (id == R.id.context_party_del) {
                            activity.partyDelete(party);
                            return true;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

        return view;
    }
}
