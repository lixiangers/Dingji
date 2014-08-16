package com.lixiangers.dingji.activity;

import android.os.Bundle;
import android.widget.Button;

import com.lixiangers.dingji.R;

public class ManagerMainActivity extends NeolixNaviagationBaseActivity {

    private Button goodsManagerButton;
    private Button orderManagerButton;
    private Button orderQueryButton;

    public ManagerMainActivity() {
        super(R.layout.activity_manager);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.system_manager));

        initView();
    }

    private void initView() {
        goodsManagerButton = (Button) findViewById(R.id.bt_goods_manager);
        orderManagerButton = (Button) findViewById(R.id.bt_backlog_order);
        orderQueryButton = (Button) findViewById(R.id.bt_order_query);
    }
}
