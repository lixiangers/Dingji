package com.lixiangers.dingji.activity;

import android.os.Bundle;

import com.lixiangers.dingji.R;

public class QueryOrderActivity extends NeolixNaviagationBaseActivity {
    public QueryOrderActivity() {
        super(R.layout.activity_query_order);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.my_order);
    }
}
