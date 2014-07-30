package com.lixiangers.dingji;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

public class ManagerActivity extends Activity {

    private Button goodsManagerButton;
    private Button orderManagerButton;
    private Button orderQueryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        initView();
    }

    private void initView() {
        goodsManagerButton = (Button) findViewById(R.id.bt_goods_manager);
        orderManagerButton = (Button) findViewById(R.id.bt_order_manager);
        orderQueryButton = (Button) findViewById(R.id.bt_order_query);
    }
}
