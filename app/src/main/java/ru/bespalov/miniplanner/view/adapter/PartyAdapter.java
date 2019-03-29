package ru.bespalov.miniplanner.view.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.android.multiselector.MultiSelector;

import ru.bespalov.miniplanner.R;
import ru.bespalov.miniplanner.model.Party;

/**
 * Created by privod on 28.10.2015.
 */
public class PartyAdapter extends BaseAdapter<PartyAdapter.PartyViewHolder, Party> {

    public PartyAdapter(MultiSelector multiSelector) {
        super(multiSelector);
    }

    class PartyViewHolder extends BaseAdapter.ViewHolder {
        private Context context;
        private TextView nameTextView;
        private TextView debtTextView;
        private ImageView avatarIcon;

        PartyViewHolder(View itemView) {
            super(itemView);

            context = itemView.getContext();

            nameTextView = (TextView) itemView.findViewById(R.id.text_view_name);
            debtTextView = (TextView) itemView.findViewById(R.id.text_view_debt);
            avatarIcon = (ImageView) itemView.findViewById(R.id.icon_avatar);
        }
    }

    @Override
    public PartyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_party, parent, false);

        return new PartyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PartyViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);

        Party party = getData().get(position);

        holder.nameTextView.setText(party.getName());
        holder.avatarIcon.setImageDrawable(newAvatarDrawable(party.getName()));

        if (party.getDebt().signum() == 0) {
            holder.debtTextView.setText(party.getOverpayView());
            holder.debtTextView.setTextColor(ContextCompat.getColor(holder.context, R.color.material_green_700));
        } else {
            holder.debtTextView.setText(party.getDebtView());
            holder.debtTextView.setTextColor(ContextCompat.getColor(holder.context, R.color.material_red_800));
        }
    }
}
