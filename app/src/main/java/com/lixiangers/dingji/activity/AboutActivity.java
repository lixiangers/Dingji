package com.lixiangers.dingji.activity;

import android.os.Bundle;

import com.lixiangers.dingji.R;

public class AboutActivity extends NeolixNaviagationBaseActivity {
    public AboutActivity() {
        super(R.layout.activity_about);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.about_ding_ji);
    }
}
