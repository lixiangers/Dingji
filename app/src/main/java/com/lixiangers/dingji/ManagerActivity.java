package com.lixiangers.dingji;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

public class ManagerActivity extends NeolixNaviagationBaseActivity {

    private Button goodsManagerButton;
    private Button orderManagerButton;
    private Button orderQueryButton;

    public ManagerActivity() {
        super(R.layout.activity_manager);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("系统管理");

        initView();
    }

    private void initView() {
        goodsManagerButton = (Button) findViewById(R.id.bt_goods_manager);
        orderManagerButton = (Button) findViewById(R.id.bt_backlog_order);
        orderQueryButton = (Button) findViewById(R.id.bt_order_query);
    }
}
