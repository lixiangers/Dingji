package com.lixiangers.dingji.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lixiangers.dingji.R;
import com.lixiangers.dingji.manager.FinishOrderListActivity;
import com.lixiangers.dingji.manager.UnFinishOrderListActivity;

public class ManagerMainActivity extends NeolixNaviagationBaseActivity {

    private Button goodsManagerButton;
    private Button unfinishOrderButton;
    private Button orderQueryButton;
    private Button finishOrderButton;

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
        unfinishOrderButton = (Button) findViewById(R.id.bt_backlog_order);
        orderQueryButton = (Button) findViewById(R.id.bt_order_query);
        finishOrderButton = (Button) findViewById(R.id.bt_completed_order);
    }

    private void initListener() {
        goodsManagerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goTo(GoodsManagerActivity.class);
            }
        });

        finishOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goTo(FinishOrderListActivity.class);
            }
        });

        unfinishOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goTo(UnFinishOrderListActivity.class);
            }
        });
    }
}
