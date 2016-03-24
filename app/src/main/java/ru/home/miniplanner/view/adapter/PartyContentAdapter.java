package ru.home.miniplanner.view.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import java.util.List;
import java.util.Map;

import ru.home.miniplanner.model.Party;

/**
 * Created by bespalov on 24.02.16.
 */
public class PartyContentAdapter extends BaseExpandableListAdapter {

    private List<Party> parties;

    @Override
    public int getGroupCount() {
        return parties.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        Party party = parties.get(groupPosition);
        return party.getBays().size() + party.getOut().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return parties.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
