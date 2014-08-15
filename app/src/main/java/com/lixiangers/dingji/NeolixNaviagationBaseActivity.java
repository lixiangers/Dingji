package com.lixiangers.dingji;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewStub;


import com.lixiangers.dingji.application.MyApplication;

import java.io.Serializable;


public abstract class NeolixNaviagationBaseActivity extends FragmentActivity {

    private int contentViewId;
    private NavigationBar navigationBar;

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
        navigationBar.setLeftButtonClickedListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void setTitle(CharSequence text) {
        navigationBar.setVisibility(View.VISIBLE);
        navigationBar.setTitle(text);
    }

    public void setTitle(int resId) {
        navigationBar.setVisibility(View.VISIBLE);
        navigationBar.setTitle(resId);
    }

    public void setRightButton(int resId) {
        navigationBar.setVisibility(View.VISIBLE);
        navigationBar.setRightButton(resId);
    }

    public void setRightButtonClickedListener(View.OnClickListener listener) {
        navigationBar.setVisibility(View.VISIBLE);
        navigationBar.setRightButtonClickedListener(listener);
    }

    public void setLeftButton(int resId) {
        navigationBar.setVisibility(View.VISIBLE);
        navigationBar.setLeftButton(resId);
    }

    public void setLeftButtonClickedListener(View.OnClickListener listener) {
        navigationBar.setVisibility(View.VISIBLE);
        navigationBar.setLeftButtonClickedListener(listener);
    }

    public void setLeftButtonVisibility(boolean isDisplay) {
        navigationBar.setVisibility(View.VISIBLE);
        navigationBar.setLeftButtonVisibility(isDisplay);
    }

    public void setRightButtonVisibility(boolean isDisplay) {
        navigationBar.setVisibility(View.VISIBLE);
        navigationBar.setRightButtonVisibility(isDisplay);
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