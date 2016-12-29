package ru.home.miniplanner.view.adapter;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.List;

import ru.home.miniplanner.R;
import ru.home.miniplanner.model.Bay;
import ru.home.miniplanner.model.Contribution;
import ru.home.miniplanner.model.Party;

/**
 * Created by privod on 28.10.2015.
 */
public class PartyAdapter extends RecyclerView.Adapter<PartyAdapter.PartyViewHolder> {
    static final String LOG_TAG = PartyAdapter.class.getSimpleName();

    private List<Party> parties;

    public PartyAdapter() {
        super();
    }

    public class PartyViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView;
        private TextView debtTextView;

        public PartyViewHolder(View itemView) {
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.text_view_name);
            debtTextView = (TextView) itemView.findViewById(R.id.text_view_debt);
        }
    }

    @Override
    public PartyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.party_view, parent, false);

        return new PartyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PartyViewHolder holder, int position) {
        Party party = getParties().get(position);

        holder.nameTextView.setText(party.getName());

        holder.debtTextView.setText(party.getDebt().abs().toPlainString());
        if (party.getDebt().compareTo(new BigDecimal("0")) > 0) {
            holder.debtTextView.setTextColor(ContextCompat.getColor(holder.debtTextView.getContext(), R.color.material_red_800));
        } else {
            holder.debtTextView.setTextColor(ContextCompat.getColor(holder.debtTextView.getContext(), R.color.material_green_700));
        }
    }

    @Override
    public int getItemCount() {
        return parties.size();
    }

    public List<Party> getParties() {
        return parties;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);

        if (null == view) {
            view = getLayout().inflate(R.layout.party_view, parent, false);
        }

        final Party party = (Party) getItem(position);

        TextView nameEditText = (TextView) view.findViewById(R.id.descriptionTextView);
        TextView debtTextView = (TextView) view.findViewById(R.id.debtTextView);

        getViewService().textViewSetText(nameEditText, party.getName());
        getViewService().textViewSetText(debtTextView, party.getDebt().abs());

        if (party.getDebt().compareTo(new BigDecimal("0")) > 0) {
            debtTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.red));
        } else {
            debtTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.green));
        }

//        Log.e(LOG_TAG, String.format("%s\t%s\t%s\t%s", party.hashCode(), party.getBays().hashCode(),  party.getOut().hashCode(), party.getName()));


        ViewGroup contentLayout = (ViewGroup) view.findViewById(R.id.contentLayout);

        // ????????????????
        if (contentLayout.getChildCount() == party.getBays().size() + party.getOut().size()) {
            Log.e(LOG_TAG, String.format("return\t%s", party.getName()));
            return view;
        }
        // ????????????????

        Log.e(LOG_TAG, String.format("%s\t%s", party.hashCode(), party.getName()));
        contentLayout.removeAllViews();
        for (Bay bay : party.getBays()) {
            String tag = Bay.EXTRA_NAME + bay.getId();
            View bayLayout;
            bayLayout = contentLayout.findViewWithTag(tag);
            if (null == bayLayout) {
                bayLayout = getLayout().inflate(R.layout.party_bay_view, contentLayout, false);
                bayLayout.setTag(tag);
                contentLayout.addView(bayLayout);
            }
            TextView descriptionTextView = (TextView) bayLayout.findViewById(R.id.bayDescriptionTextView);
            TextView costTextView = (TextView) bayLayout.findViewById(R.id.costTextView);
            getViewService().textViewSetText(descriptionTextView, bay.getDescription());
            getViewService().textViewSetText(costTextView, bay.getCost());
        }
        for (Contribution contribution : party.getOut()) {
            String tag = Contribution.EXTRA_NAME + contribution.getId();
            View contributionLayout;
            contributionLayout = contentLayout.findViewWithTag(tag);
            if (null == contributionLayout) {
                contributionLayout = getLayout().inflate(R.layout.party_contribution_view, contentLayout, false);
                contributionLayout.setTag(tag);
                contentLayout.addView(contributionLayout);
            }
//            View contributionLayout = getLayout().inflate(R.layout.party_contribution_view, contentLayout, false);
            TextView partyToTextView = (TextView) contributionLayout.findViewById(R.id.partyToTextView);
            TextView sumTextView = (TextView) contributionLayout.findViewById(R.id.sumTextView);
            getViewService().textViewSetText(partyToTextView, contribution.getTo().toString());
            getViewService().textViewSetText(sumTextView, contribution.getSum());
//            contentLayout.addView(contributionLayout);
        }

//        StringBuilder bayNamesStr = new StringBuilder();
//        StringBuilder bayCostsStr = new StringBuilder();
//        for (Bay bay : party.getBays()) {
//            bayNamesStr.append(bay.getDescription()).append("\n");
//            bayCostsStr.append(bay.getCost()).append("\n");
//        }
//        TextView bayNameTextView = (TextView) view.findViewById(R.id.bayNameTextView);
//        TextView bayCostTextView = (TextView) view.findViewById(R.id.bayCostTextView);
//        getViewService().textViewSetText(bayNameTextView, bayNamesStr.toString());
//        getViewService().textViewSetText(bayCostTextView, bayCostsStr.toString());
//        if (bayCostsStr.toString().isEmpty()) {
////            ((LinearLayout) view.findViewById(R.id.titleLayout)).setVisibility(View.GONE);
//            ((LinearLayout) view.findViewById(R.id.bodyLayout)).setVisibility(View.GONE);
//        } else {
////            ((LinearLayout) view.findViewById(R.id.titleLayout)).setVisibility(View.VISIBLE);
//            ((LinearLayout) view.findViewById(R.id.bodyLayout)).setVisibility(View.VISIBLE);
//        }

        return view;
    }
}
