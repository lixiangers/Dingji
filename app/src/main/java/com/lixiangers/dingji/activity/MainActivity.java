package com.lixiangers.dingji.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTabHost;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.lixiangers.dingji.R;


public class MainActivity extends NeolixNaviagationBaseActivity {
    public static final String TAB_ACCEPT_ORDER = "tab_accept_order";
    public static final String TAB_DELIVERY = "tab_delivery";
    public static final String TAG_PICK_UP = "tag_pick_up";

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

    private void initTabHost() {
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.real_tab_content);

        RelativeLayout acceptOrderTab = createIndicator(getString(R.string.accept_order_text), R.drawable.selector_tab_host_accept);
        RelativeLayout pickUpTab = createIndicator(getString(R.string.pick_up), R.drawable.selector_tab_host_pick_up);
        RelativeLayout deliveryTab = createIndicator(getString(R.string.delivery), R.drawable.selector_tab_host_delivery);

        mTabHost.addTab(mTabHost.newTabSpec(TAB_ACCEPT_ORDER).setIndicator(acceptOrderTab),
                FragmentAcceptOrder.class, null);

        mTabHost.addTab(mTabHost.newTabSpec(TAG_PICK_UP).setIndicator(pickUpTab),
                FragmentPickUp.class, null);

        mTabHost.addTab(mTabHost.newTabSpec(TAB_DELIVERY).setIndicator(deliveryTab),
                FragmentDelivery.class, null);
    }

    private RelativeLayout createIndicator(String tabText, int icoResourceId) {
        RelativeLayout articleTab = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.tab_host_view, null);
        TextView articleTabLabel = (TextView) articleTab.findViewById(R.id.tab_label);
        articleTabLabel.setText(tabText);
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
