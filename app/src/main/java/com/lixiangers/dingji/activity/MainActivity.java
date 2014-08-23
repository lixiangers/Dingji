package com.lixiangers.dingji.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTabHost;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.lixiangers.dingji.R;
import com.lixiangers.dingji.util.Constant;


public class MainActivity extends NeolixBaseActivity {
    private FragmentTabHost mTabHost;
    private boolean isExit;

    public MainActivity() {
        super(R.layout.activity_main);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTabHost();
        mTabHost.setCurrentTab(1);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != KeyEvent.KEYCODE_BACK)
            return super.onKeyDown(keyCode, event);
        else {
            exit();
            return false;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String tag = intent.getStringExtra(Constant.TAB_TAG);
        mTabHost.setCurrentTabByTag(tag);
    }

    private void initTabHost() {
        mTabHost = (FragmentTabHost) findViewById(R.id.tab_host);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.real_tab_content);

        RelativeLayout acceptOrderTab = createIndicator(R.drawable.selector_bg_back);
        RelativeLayout pickUpTab = createIndicator(R.drawable.selector_bg_back);
        RelativeLayout deliveryTab = createIndicator(R.drawable.selector_bg_back);

        mTabHost.addTab(mTabHost.newTabSpec(Constant.TAB_SHOPPING_CART).setIndicator(acceptOrderTab),
                ShoppingCartFragment.class, null);

        mTabHost.addTab(mTabHost.newTabSpec(Constant.TAB_GOODS).setIndicator(pickUpTab),
                BrowseGoodsFragment.class, null);

        mTabHost.addTab(mTabHost.newTabSpec(Constant.TAG_SETTING).setIndicator(deliveryTab),
                AccountSettingFragment.class, null);
    }

    private RelativeLayout createIndicator(int icoResourceId) {
        RelativeLayout articleTab = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.tab_host_view, null);
        ImageView imageSwitcher = (ImageView) articleTab.findViewById(R.id.tab_image_view);
        imageSwitcher.setImageResource(icoResourceId);
        return articleTab;
    }

    private void exit() {
        if (isExit) {
            moveTaskToBack(true);
        } else {
            isExit = true;
            Toast.makeText(getApplicationContext(), getString(R.string.please_press_again_to_exit_app), Toast.LENGTH_SHORT).show();
            mHandler.sendEmptyMessageDelayed(0, 300);
        }
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };
}
