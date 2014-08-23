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
import com.lixiangers.dingji.view.GoodsItemView;
import com.lixiangers.dingji.viewmodel.GoodsItemViewModel;

import java.util.List;

public class GoodsExpandeAdapter extends BaseExpandableListAdapter {
    private final Context context;
    private final LayoutInflater inflater;
    private List<GoodsCategory> data = null;

    private onByGoodsListener onByGoodsListener = new onByGoodsListener() {
        @Override
        public void OnByGoods(Goods goods) {
        }
    };

    public void setOnByGoodsListener(GoodsExpandeAdapter.onByGoodsListener onByGoodsListener) {
        this.onByGoodsListener = onByGoodsListener;
    }

    public GoodsExpandeAdapter(Context ctx, List<GoodsCategory> list) {
        context = ctx;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        data = list;
    }

    public void setData(List<GoodsCategory> list) {
        data = list;
        notifyDataSetChanged();
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
            convertView = new GoodsItemView(context);
        }
        GoodsItemViewModel itemViewModel = new GoodsItemViewModel();
        final Goods child = getChild(groupPosition, childPosition);
        itemViewModel.setGoods(child);
        itemViewModel.setShowByView(true);
        itemViewModel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onByGoodsListener.OnByGoods(child);
            }
        });
        ((GoodsItemView) convertView).setModel(itemViewModel);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public interface onByGoodsListener {
        void OnByGoods(Goods goods);
    }
}
