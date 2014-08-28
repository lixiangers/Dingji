package com.lixiangers.dingji.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.reflect.TypeToken;
import com.lixiangers.dingji.R;
import com.lixiangers.dingji.protocol.domain.LoginRequest;
import com.lixiangers.dingji.protocol.domain.LoginResponse;
import com.lixiangers.dingji.protocol.http.HttpRequest;
import com.lixiangers.dingji.protocol.http.HttpResponse;
import com.lixiangers.dingji.protocol.http.RequestServerAsyncTask;
import com.lixiangers.dingji.protocol.http.RequestType;

import java.lang.reflect.Type;

import static com.lixiangers.dingji.application.MyApplication.getInstance;
import static com.lixiangers.dingji.util.DialogFactory.hideRequestDialog;
import static com.lixiangers.dingji.util.DialogFactory.showRequestDialog;
import static com.lixiangers.dingji.util.SharedPreferencesUtil.saveToken;
import static com.lixiangers.dingji.util.SharedPreferencesUtil.saveUserPhone;
import static com.lixiangers.dingji.util.StringUtil.getTextFrom;
import static com.lixiangers.dingji.util.StringUtil.isBlank;
import static com.lixiangers.dingji.util.StringUtil.showText;

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

        accountEditText.setText("test01");
        pwdEditText.setText("123456");
    }

    private void initListener() {
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = getTextFrom(accountEditText);
                String pwd = getTextFrom(pwdEditText);

                if (isBlank(userName)) {
                    showText("请输入账号");
                    accountEditText.requestFocus();
                    return;
                }

                if (isBlank(pwd)) {
                    showText("请输入密码");
                    pwdEditText.requestFocus();
                    return;
                }
                login(userName, pwd);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goTo(RegisterActivity.class);
            }
        });
    }

    private void login(final String userName, String password) {
        LoginRequest params = new LoginRequest(userName, password);
        final HttpRequest httpRequest = new HttpRequest(
                RequestType.user_login, params);

        Type type = new TypeToken<HttpResponse<LoginResponse>>() {
        }.getType();

        showRequestDialog(LoginActivity.this, R.string.is_loading);
        RequestServerAsyncTask<HttpResponse<LoginResponse>> task =
                new RequestServerAsyncTask<HttpResponse<LoginResponse>>(type) {
                    @Override
                    public void OnResponse(HttpResponse<LoginResponse> httpResponse) {
                        hideRequestDialog();
                        if (httpResponse.noErrorMessage()) {
                            getInstance().loginApp();
                            saveToken(httpResponse.getResponseParams().getToken());
                            saveUserPhone(userName);
                            boolean isAdmin = httpResponse.getResponseParams().is_superuser();
                            startActivity(new Intent(getInstance(), isAdmin ? ManagerMainActivity.class : MainActivity.class));
                        } else
                            showText(httpResponse.getError().getMessage());
                    }
                };
        task.sendRequest(httpRequest, false);
    }
}
