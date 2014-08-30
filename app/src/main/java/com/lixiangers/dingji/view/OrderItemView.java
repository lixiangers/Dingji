package com.lixiangers.dingji.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.lixiangers.dingji.R;
import com.lixiangers.dingji.adapter.BaseItemView;
import com.lixiangers.dingji.viewmodel.GoodsItemViewModel;

public class OrderItemView extends BaseItemView<GoodsItemViewModel> {
    private final Context context;
    private TextView orderNumberTextView;
    private TextView orderTimeTextView;

    public OrderItemView(Context context) {
        super(context);
        this.context = context;
        initUi();
    }

    public OrderItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initUi();
    }

    @Override
    public void setModel(GoodsItemViewModel model) {
        super.setModel(model);
    }

    private void initUi() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_order_item, this);

        orderNumberTextView = (TextView) findViewById(R.id.tv_order_number);
        orderTimeTextView = (TextView) findViewById(R.id.tv_order_time);
    }
}
