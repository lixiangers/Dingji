package com.lixiangers.dingji.activity;

import android.os.Bundle;

import com.lixiangers.dingji.R;

public class GoodsManagerActivity extends NeolixNaviagationBaseActivity {
    public GoodsManagerActivity() {
        super(R.layout.activity_goods_manager);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.goods_manager);
    }
}
