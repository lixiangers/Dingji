package com.lixiangers.dingji.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.lixiangers.dingji.R;
import com.lixiangers.dingji.adapter.BaseItemView;
import com.lixiangers.dingji.util.StringUtil;

import static com.lixiangers.dingji.protocol.domain.QueryOrderDetailResponse.OrderRoute;

public class OrderRouteView extends BaseItemView<OrderRoute> {
    private final Context context;
    private TextView orderStatusTextView;
    private TextView TimeTextView;

    public OrderRouteView(Context context) {
        super(context);
        this.context = context;
        initUi();
    }

    public OrderRouteView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initUi();
    }

    @Override
    public void setModel(OrderRoute model) {
        super.setModel(model);

        orderStatusTextView.setText(model.getDescription());
        TimeTextView.setText(StringUtil.formatTimeWithoutSecond(model.getAction_time()));
    }

    private void initUi() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_order_status, this);

        TimeTextView = (TextView) findViewById(R.id.tv_time);
        orderStatusTextView = (TextView) findViewById(R.id.tv_order_status);
    }
}
