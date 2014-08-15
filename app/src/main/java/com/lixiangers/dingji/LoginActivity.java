package com.lixiangers.dingji;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
                startActivity(new Intent(getApplicationContext(), ManagerActivity.class));
            }
        });
    }
}
