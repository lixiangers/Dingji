package com.lixiangers.dingji.activity;

import android.os.Bundle;
import android.view.View;
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
        initListener();
    }

    private void initView() {
        goodsManagerButton = (Button) findViewById(R.id.bt_goods_manager);
        orderManagerButton = (Button) findViewById(R.id.bt_backlog_order);
        orderQueryButton = (Button) findViewById(R.id.bt_order_query);
    }

    private void initListener() {
        goodsManagerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goTo(GoodsManagerActivity.class);
            }
        });
    }
}
