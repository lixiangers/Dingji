package com.lixiangers.dingji.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.ViewStub;


import com.lixiangers.dingji.R;
import com.lixiangers.dingji.application.MyApplication;

import java.io.Serializable;


public abstract class NeolixBaseActivity extends FragmentActivity {

    private int contentViewId;

    public NeolixBaseActivity(int contentViewStubId) {
        this.contentViewId = contentViewStubId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neolix_base);

        initContentViewStub();
    }

    public void goTo(Class<? extends Activity> clazz) {
        Intent intent = getIntent(clazz);
        startActivity(intent);
    }

    protected void goToNextWithBundle(Serializable serializable, Class<? extends Activity> clazz, String tag) {
        Intent intent = getIntent(clazz);
        intent.putExtra(tag, serializable);
        startActivity(intent);
    }

    private Intent getIntent(Class<? extends Activity> clazz) {
        return new Intent(MyApplication.getInstance(), clazz);
    }

    private void initContentViewStub() {
        ViewStub viewStub = (ViewStub) findViewById(R.id.wepay_content_view_stub);
        viewStub.setLayoutResource(contentViewId);
        viewStub.inflate();
    }
}