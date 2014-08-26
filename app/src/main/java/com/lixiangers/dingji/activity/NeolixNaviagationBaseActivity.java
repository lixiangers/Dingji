package com.lixiangers.dingji.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewStub;

import com.lixiangers.dingji.R;
import com.lixiangers.dingji.application.MyApplication;
import com.lixiangers.dingji.view.NavigationBar;

import java.io.Serializable;

import static android.view.View.OnClickListener;


public abstract class NeolixNaviagationBaseActivity extends FragmentActivity {

    private int contentViewId;
    private NavigationBar navigationBar;
    private OnClickListener listener;

    public NeolixNaviagationBaseActivity(int contentViewStubId) {
        this.contentViewId = contentViewStubId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neolix_naviagtion_base);

        initNavBar();
        initContentViewStub();
    }

    private void initNavBar() {
        navigationBar = (NavigationBar) findViewById(R.id.navigation_bar);
        listener = new OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        };
        navigationBar.setLeftImage(R.drawable.selector_bg_back, listener);
    }

    public void setTitle(CharSequence text) {
        navigationBar.setVisibility(View.VISIBLE);
        navigationBar.setTitle(text);
    }

    public void setTitle(int resId) {
        navigationBar.setVisibility(View.VISIBLE);
        navigationBar.setTitle(resId);
    }

    public void setRightImage(int resId, OnClickListener listener) {
        navigationBar.setVisibility(View.VISIBLE);
        navigationBar.setRightImage(resId, listener);
    }

    public void setLeftImage(int resId, OnClickListener listener) {
        navigationBar.setVisibility(View.VISIBLE);
        navigationBar.setLeftImage(resId, listener);
    }

    public void setLeftImage(int resId) {
        navigationBar.setVisibility(View.VISIBLE);
        navigationBar.setLeftImage(resId, listener);
    }

    public void setRightText(int resId, OnClickListener listener) {
        navigationBar.setVisibility(View.VISIBLE);
        navigationBar.setRightText(resId, listener);
    }

    public void setRightText(CharSequence text, OnClickListener listener) {
        navigationBar.setVisibility(View.VISIBLE);
        navigationBar.setRightText(text, listener);
    }

    public void setCenterImageResource(int resource) {
        navigationBar.setVisibility(View.VISIBLE);
        navigationBar.setCenterImageResource(resource);
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

    public void hideNavigationBar() {
        navigationBar.setVisibility(View.GONE);
    }
}