package com.lixiangers.dingji.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Adapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lixiangers.dingji.R;
import com.lixiangers.dingji.model.Goods;
import com.lixiangers.dingji.protocol.domain.GoodsCategory;
import com.lixiangers.dingji.view.GoodsItemView;
import com.lixiangers.dingji.viewmodel.GoodsItemViewModel;

import java.util.ArrayList;
import java.util.List;

public class GoodsExpandeAdapter extends BaseExpandableListAdapter {
    private final Context context;
    private final LayoutInflater inflater;
    private List<GoodsCategory> data = new ArrayList<GoodsCategory>();

    private RotateAnimation mRotateUpAnim;
    private RotateAnimation mRotateDownAnim;
    private static final int ROTATE_ANIM_DURATION = 150;

    private onByGoodsListener onByGoodsListener = new onByGoodsListener() {
        @Override
        public void OnByGoods(Goods goods) {
        }
    };
    private int selectedGroupPosition;

    public void setSelectedGroupPosition(int position) {
        if (!isPositionInRange(position)) {
            selectedGroupPosition = Adapter.NO_SELECTION;
            return;
        }

        this.selectedGroupPosition = position;

        notifyDataSetChanged();
    }

    public boolean isPositionInRange(int position) {
        return position >= 0 && position < getGroupCount();
    }

    public void setOnByGoodsListener(GoodsExpandeAdapter.onByGoodsListener onByGoodsListener) {
        this.onByGoodsListener = onByGoodsListener;
    }

    public GoodsExpandeAdapter(Context ctx, List<GoodsCategory> list) {
        context = ctx;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        data = list;


        float pivotValue = 0.5f;    // SUPPRESS CHECKSTYLE
        float toDegree = -180f;     // SUPPRESS CHECKSTYLE

        // 初始化旋转动画
        mRotateUpAnim = new RotateAnimation(0.0f, toDegree, Animation.RELATIVE_TO_SELF, pivotValue,
                Animation.RELATIVE_TO_SELF, pivotValue);

        mRotateUpAnim.setDuration(ROTATE_ANIM_DURATION);
        mRotateUpAnim.setFillAfter(true);
        mRotateDownAnim = new RotateAnimation(toDegree, 0.0f, Animation.RELATIVE_TO_SELF, pivotValue,
                Animation.RELATIVE_TO_SELF, pivotValue);
        mRotateDownAnim.setDuration(ROTATE_ANIM_DURATION);
        mRotateDownAnim.setFillAfter(true);
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
        if (data.size() != 0 && data.get(groupPosition).getData() != null)
            return data.get(groupPosition).getData().size();
        else {
            return 0;
        }
    }

    @Override
    public GoodsCategory getGroup(int groupPosition) {
        return data.get(groupPosition);
    }

    @Override
    public Goods getChild(int groupPosition, int childPosition) {
        return data.get(groupPosition).getData().get(childPosition);
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
        groupName.setText(data.get(groupPosition).getCategory());

        ImageView group_indicator_image = (ImageView) convertView.findViewById(R.id.iv_group_indicator);
        group_indicator_image.setImageResource(isExpanded ? R.drawable.ic_arrow_up : R.drawable.ic_arrow_down);
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
