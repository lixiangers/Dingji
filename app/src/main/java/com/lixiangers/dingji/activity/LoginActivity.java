package com.lixiangers.dingji.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.lixiangers.dingji.R;
import com.lixiangers.dingji.application.MyApplication;

import static com.lixiangers.dingji.util.StringUtil.getTextFrom;

public class LoginActivity extends NeolixBaseActivity {

    public static final String TEST_USER = "lixiang";
    private EditText accountEditText;
    private EditText pwdEditText;
    private Button loginButton;
    private Button registerButton;

    public LoginActivity() {
        super(R.layout.activity_login);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initListener();
    }

    private void initView() {
        accountEditText = (EditText) findViewById(R.id.et_account);
        pwdEditText = (EditText) findViewById(R.id.et_pwd);
        loginButton = (Button) findViewById(R.id.bt_login);
        registerButton = (Button) findViewById(R.id.bt_register);

        accountEditText.setText(TEST_USER);
    }

    private void initListener() {
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getTextFrom(accountEditText).equals(TEST_USER)) {
                    MyApplication.getInstance().loginApp();
                    goTo(BrowseGoodsActivity.class);
                } else {
                    MyApplication.getInstance().loginApp();
                    goTo(ManagerMainActivity.class);
                }
            }
        });
    }
}
