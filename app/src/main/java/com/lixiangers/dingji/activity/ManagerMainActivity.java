package com.lixiangers.dingji.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.lixiangers.dingji.R;
import com.lixiangers.dingji.manager.FinishOrderListActivity;
import com.lixiangers.dingji.manager.UnFinishOrderListActivity;
import com.lixiangers.dingji.util.Constant;

import static com.lixiangers.dingji.util.StringUtil.showText;

public class ManagerMainActivity extends NeolixNaviagationBaseActivity {

    private Button goodsManagerButton;
    private Button unfinishOrderButton;
    private Button orderQueryButton;
    private Button finishOrderButton;
    private boolean isExit;

    public ManagerMainActivity() {
        super(R.layout.activity_manager);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.system_manager));

        initView();
        initListener();
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

    private void initView() {
        goodsManagerButton = (Button) findViewById(R.id.bt_goods_manager);
        unfinishOrderButton = (Button) findViewById(R.id.bt_backlog_order);
        orderQueryButton = (Button) findViewById(R.id.bt_order_query);
        finishOrderButton = (Button) findViewById(R.id.bt_completed_order);
    }

    private void initListener() {
        goodsManagerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goTo(GoodsManagerActivity.class);
            }
        });

        finishOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goTo(FinishOrderListActivity.class);
            }
        });

        unfinishOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goTo(UnFinishOrderListActivity.class);
            }
        });

        orderQueryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNextWithBundle(true, QueryOrderActivity.class, Constant.IS_ADMIN);
            }
        });
    }

    private void exit() {
        if (isExit) {
            finish();
        } else {
            isExit = true;
            showText(R.string.please_press_again_to_login_activity);
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
