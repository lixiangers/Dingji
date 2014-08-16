package com.lixiangers.dingji.activity;

import android.os.Bundle;
import android.view.View;

import com.lixiangers.dingji.R;

public class GoodsManagerActivity extends NeolixNaviagationBaseActivity {

    private View addGoodsView;

    public GoodsManagerActivity() {
        super(R.layout.activity_goods_manager);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.goods_manager);

        addGoodsView = findViewById(R.id.iv_add_goods);
        addGoodsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goTo(AddGoodsActivity.class);
            }
        });
    }
}
