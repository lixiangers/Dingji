package com.lixiangers.dingji.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.lixiangers.dingji.R;
import com.lixiangers.dingji.application.MyApplication;

public class LoginActivity extends NeolixBaseActivity {

    private EditText accountEditText;
    private EditText pwdEditText;
    private Button loginButton;

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
    }

    private void initListener() {
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.getInstance().loginApp();
                startActivity(new Intent(getApplicationContext(), ManagerMainActivity.class));
            }
        });
    }
}
