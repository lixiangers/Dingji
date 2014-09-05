package com.lixiangers.dingji.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lixiangers.dingji.R;
import com.lixiangers.dingji.model.Problem;

import java.util.ArrayList;
import java.util.List;

public class ProblemExpandeAdapter extends BaseExpandableListAdapter {
    private final Context context;
    private final LayoutInflater inflater;
    private List<Problem> data = new ArrayList<Problem>();

    public ProblemExpandeAdapter(Context ctx) {
        context = ctx;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData(List<Problem> list) {
        data = list;
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return data.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public String getGroup(int groupPosition) {
        return data.get(groupPosition).getQ();
    }

    @Override
    public String getChild(int groupPosition, int childPosition) {
        return data.get(groupPosition).getA();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.problem_group_item_layout, null);
        }
        TextView groupName = (TextView) convertView
                .findViewById(R.id.group_name);
        groupName.setText(data.get(groupPosition).getQ());

        ImageView group_indicator_image = (ImageView) convertView.findViewById(R.id.iv_group_indicator);
        group_indicator_image.setImageResource(isExpanded ? R.drawable.ic_arrow_up : R.drawable.ic_arrow_down);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.problem_child_item_layout, null);
        }
        TextView content = (TextView) convertView
                .findViewById(R.id.tv_content);
        content.setText(data.get(groupPosition).getA());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
