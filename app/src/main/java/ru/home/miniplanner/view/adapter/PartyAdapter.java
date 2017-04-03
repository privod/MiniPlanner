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

import com.bignerdranch.android.multiselector.MultiSelector;
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
public class PartyAdapter extends BaseAdapter<PartyAdapter.PartyViewHolder, Party> {

//    private List<Party> parties;

    public PartyAdapter(MultiSelector multiSelector) {
        super(multiSelector, PartyAdapter.PartyViewHolder.class);
    }

    class PartyViewHolder extends BaseAdapter.ViewHolder {
        private Context context;
        private TextView nameTextView;
        private TextView debtTextView;

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
        Party party = getData().get(position);

        holder.nameTextView.setText(party.getName());

//        holder.debtTextView.setText(party.getDebt().abs().toPlainString());
        if (null != party.getDebt()) {
            holder.debtTextView.setText(party.getDebt().toPlainString());
            holder.debtTextView.setTextColor(ContextCompat.getColor(holder.context, R.color.material_red_800));
        } else {
            holder.debtTextView.setText(party.getOverpay().toPlainString());
            holder.debtTextView.setTextColor(ContextCompat.getColor(holder.context, R.color.material_green_700));
        }
    }
}
