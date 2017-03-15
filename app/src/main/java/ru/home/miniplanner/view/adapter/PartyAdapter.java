package ru.home.miniplanner.view.adapter;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.support.annotation.NonNull;
import android.support.transition.TransitionManager;
import android.support.v4.content.ContextCompat;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ChildViewHolder;
import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.ParentViewHolder;

import java.util.ArrayList;

import ru.home.miniplanner.R;
import ru.home.miniplanner.model.Bay;
import ru.home.miniplanner.model.Party;
import ru.home.miniplanner.model.PartyContent;
import ru.home.miniplanner.view.PartiesActivity;

/**
 * Created by privod on 28.10.2015.
 */
//public class PartyAdapter extends RecyclerView.Adapter<PartyAdapter.PartyViewHolder> {
public class PartyAdapter extends ExpandableRecyclerAdapter<Party, PartyContent, PartyAdapter.PartyViewHolder, PartyAdapter.PartyContentViewHolder> {

//    private List<Party> parties;

    public PartyAdapter() {
        super(new ArrayList<Party>());
//        parties = new ArrayList<>();
    }

    class PartyViewHolder extends ParentViewHolder {
        private Context context;
        private ImageView expandImageView;
        private TextView nameTextView;
        private TextView debtTextView;
        private ViewGroup expandLayout;
//        private ViewGroup partyContentLayout;

        public PartyViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Click action
//                    if (v.getContext() instanceof PartiesActivity) {
//                        ((PartiesActivity) v.getContext()).startPartyContentActivity(getAdapterPosition());
//                    }
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return v.getContext() instanceof PartiesActivity;

                }
            });

            context = itemView.getContext();

            expandLayout = (ViewGroup) itemView.findViewById(R.id.layout_expand);
//            final TransitionDrawable transition = new TransitionDrawable(new Drawable[] {
//                    context.getResources().getDrawable(R.drawable.ic_keyboard_arrow_up_black_24dp),
//                    context.getResources().getDrawable(R.drawable.ic_keyboard_arrow_down_black_24dp)
//            });
            expandImageView = (ImageView) itemView.findViewById(R.id.image_view_expand);
//            expandImageView.setImageDrawable(transition);
            expandImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isExpanded()) {
//                        transition.startTransition(1000);
                        collapseView();
                    } else {
//                        transition.reverseTransition(1000);
                        expandView();
                    }
                }
            });

            nameTextView = (TextView) itemView.findViewById(R.id.text_view_name);
            debtTextView = (TextView) itemView.findViewById(R.id.text_view_debt);
//            partyContentLayout = (ViewGroup) itemView.findViewById(R.id.partyContentLayout);
        }

        @Override
        public void onExpansionToggled(boolean expanded) {
            super.onExpansionToggled(expanded);

            TransitionManager.beginDelayedTransition(expandLayout);
            expandImageView.setRotation(180);
        }
    }

    class PartyContentViewHolder extends ChildViewHolder {
//        private Context context;
        private ImageView iconImageView;
        private TextView descriptionTextView;
        private TextView sumTextView;
//        private ViewGroup partyContentLayout;

        PartyContentViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Click action
//                    if (v.getContext() instanceof PartiesActivity) {
//                        ((PartiesActivity) v.getContext()).startPartyContentActivity(getAdapterPosition());
//                    }
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (v.getContext() instanceof PartiesActivity) {
                        // TODO Long click action
                        return true;
                    }

                    return false;
                }
            });

//            context = itemView.getContext();
            iconImageView = (ImageView) itemView.findViewById(R.id.image_view_icon);
            descriptionTextView = (TextView) itemView.findViewById(R.id.text_view_description);
            sumTextView = (TextView) itemView.findViewById(R.id.text_view_sum);
//            partyContentLayout = (ViewGroup) itemView.findViewById(R.id.partyContentLayout);
        }
    }

    @NonNull
    @Override
    public PartyViewHolder onCreateParentViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.party_view, parent, false);

        return new PartyViewHolder(view);
    }

    @NonNull
    @Override
    public PartyContentViewHolder onCreateChildViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.party_contribution_view, parent, false);

        return new PartyContentViewHolder(view);
    }

    @Override
//    public void onBindParentViewHolder(PartyViewHolder holder, int position) {
    public void onBindParentViewHolder(@NonNull PartyViewHolder holder, int parentPosition, @NonNull Party party) {
//        Party party = getParties().get(position);

        holder.nameTextView.setText(party.getName());

//        holder.debtTextView.setText(party.getDebt().abs().toPlainString());
        if (null != party.getDebt()) {
            holder.debtTextView.setText(party.getDebt().toPlainString());
            holder.debtTextView.setTextColor(ContextCompat.getColor(holder.context, R.color.material_red_800));
        } else {
            holder.debtTextView.setText(party.getOverpay().toPlainString());
            holder.debtTextView.setTextColor(ContextCompat.getColor(holder.context, R.color.material_green_700));
        }

        /*LayoutInflater inflater = LayoutInflater.from(holder.context);

        holder.partyContentLayout.removeAllViews();
        for (Bay bay : party.getBays()) {
            String tag = Bay.EXTRA_NAME + bay.getId();
            View bayLayout;
            bayLayout = holder.partyContentLayout.findViewWithTag(tag);
            if (null == bayLayout) {
                bayLayout = inflater.inflate(R.layout.party_bay_view, holder.partyContentLayout, false);
                bayLayout.setTag(tag);
                holder.partyContentLayout.addView(bayLayout);
            }
            TextView descriptionTextView = (TextView) bayLayout.findViewById(R.id.bayDescriptionTextView);
            TextView costTextView = (TextView) bayLayout.findViewById(R.id.costTextView);
            descriptionTextView.setText(bay.getDescription());
            costTextView.setText(bay.getCost().toPlainString());
        }
        for (Contribution contribution : party.getOut()) {
            String tag = Contribution.EXTRA_NAME + contribution.getId();
            View contributionLayout;
            contributionLayout = holder.partyContentLayout.findViewWithTag(tag);
            if (null == contributionLayout) {
                contributionLayout = inflater.inflate(R.layout.party_contribution_view, holder.partyContentLayout, false);
                contributionLayout.setTag(tag);
                holder.partyContentLayout.addView(contributionLayout);
            }
//            View contributionLayout = getLayout().inflate(R.layout.party_contribution_view, contentLayout, false);
            TextView partyToTextView = (TextView) contributionLayout.findViewById(R.id.partyToTextView);
            TextView sumTextView = (TextView) contributionLayout.findViewById(R.id.sumTextView);
            partyToTextView.setText(contribution.getTo().toString());
            sumTextView.setText(contribution.getSum().toPlainString());
//            contentLayout.addView(contributionLayout);
        }*/
    }

    @Override
    public void onBindChildViewHolder(@NonNull PartyContentViewHolder holder, int parentPosition, int childPosition, @NonNull PartyContent child) {

        if (child instanceof Bay) {
            holder.iconImageView.setImageResource(R.drawable.ic_cart);
        } else {
            holder.iconImageView.setImageResource(R.drawable.ic_cash_multiple);
        }

        holder.descriptionTextView.setText(child.getDescription());
        holder.sumTextView.setText(child.getSum().toPlainString());
    }

//    @Override
//    public int getItemCount() {
//        return parties.size();
//    }

//    public List<Party> getParties() {
//        return parties;
//    }

//    public void updateParties(List<Party> newParties) {
//        setParentList(newParties);
//        notifyDataSetChanged();
//    }

    /*@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);

        if (null == view) {
            view = getLayout().inflate(R.layout.party_view, parent, false);
        }

        final Party party = (Party) getItem(position);

        TextView nameEditText = (TextView) view.findViewById(R.id.descriptionTextView);
        TextView costTextView = (TextView) view.findViewById(R.id.costTextView);

        getViewService().textViewSetText(nameEditText, party.getName());
        getViewService().textViewSetText(costTextView, party.getDebt().abs());

        if (party.getDebt().compareTo(new BigDecimal("0")) > 0) {
            costTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.red));
        } else {
            costTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.green));
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
    }*/
}
