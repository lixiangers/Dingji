package com.lixiangers.dingji.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lixiangers.dingji.R;
import com.lixiangers.dingji.dao.Goods;
import com.lixiangers.dingji.protocol.domain.GoodsCategory;

import java.util.List;

public class GoodsExpandeAdapter extends BaseExpandableListAdapter {
    private final Context context;
    private final LayoutInflater inflater;
    private List<GoodsCategory> data = null;

    public GoodsExpandeAdapter(Context ctx, List<GoodsCategory> list) {
        context = ctx;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        data = list;
    }

    @Override
    public int getGroupCount() {
        return data.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return data.get(groupPosition).getGoodsList().size();
    }

    @Override
    public GoodsCategory getGroup(int groupPosition) {
        return data.get(groupPosition);
    }

    @Override
    public Goods getChild(int groupPosition, int childPosition) {
        return data.get(groupPosition).getGoodsList().get(childPosition);
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
            convertView = inflater.inflate(R.layout.group_item_layout, null);
        }
        TextView groupName = (TextView) convertView
                .findViewById(R.id.group_name);
        groupName.setText(data.get(groupPosition).getCategoryName());

        ImageView group_indicator_image = (ImageView) convertView.findViewById(R.id.iv_group_indicator);
        group_indicator_image.setImageResource(isExpanded ? R.drawable.ic_back_hl : R.drawable.ic_back);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.child_item_layout, null);
        }

        TextView childName = (TextView) convertView.findViewById(R.id.item_name);
        childName.setText(getChild(groupPosition, childPosition)
                .getName());

        TextView priceText = (TextView) convertView.findViewById(R.id.item_detail);
        priceText.setText(getChild(groupPosition, childPosition)
                .getUnit() + "/" + getChild(groupPosition, childPosition).getPriceOfYuan());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
