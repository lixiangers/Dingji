package com.lixiangers.dingji.activity;

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

import static com.lixiangers.dingji.util.DialogFactory.hideRequestDialog;
import static com.lixiangers.dingji.util.DialogFactory.showRequestDialog;
import static com.lixiangers.dingji.util.SharedPreferencesUtil.savePwd;
import static com.lixiangers.dingji.util.SharedPreferencesUtil.saveToken;
import static com.lixiangers.dingji.util.SharedPreferencesUtil.saveUserPhone;
import static com.lixiangers.dingji.util.StringUtil.getTextFrom;
import static com.lixiangers.dingji.util.StringUtil.isBlank;
import static com.lixiangers.dingji.util.StringUtil.showText;

public class RegisterActivity extends NeolixNaviagationBaseActivity {

    private EditText accountEditText;
    private EditText pwdEditText;
    private EditText confirmPwdEditText;
    private Button registerButton;

    public RegisterActivity() {
        super(R.layout.activity_register);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.register);

        initView();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = getTextFrom(accountEditText);
                String pwd = getTextFrom(pwdEditText);
                String confirmPwd = getTextFrom(confirmPwdEditText);

                if (isBlank(account) || isBlank(pwd)) {
                    showText(R.string.data_incomplete);
                    return;
                }

                if (!pwd.equals(confirmPwd)) {
                    showText(getString(R.string.the_two_passwords_you_entered_do_not_match));
                    confirmPwdEditText.requestFocus();
                    return;
                }

                register(account, pwd);
            }
        });
    }

    private void initView() {
        accountEditText = (EditText) findViewById(R.id.et_account);
        pwdEditText = (EditText) findViewById(R.id.et_pwd);
        confirmPwdEditText = (EditText) findViewById(R.id.et_confirm_pwd);

        registerButton = (Button) findViewById(R.id.bt_register);
    }

    private void register(final String userName, final String password) {
        LoginRequest params = new LoginRequest(userName, password);
        final HttpRequest httpRequest = new HttpRequest(
                RequestType.user_register, params);

        Type type = new TypeToken<HttpResponse<LoginResponse>>() {
        }.getType();

        showRequestDialog(RegisterActivity.this, getString(R.string.is_register));
        RequestServerAsyncTask<HttpResponse<LoginResponse>> task =
                new RequestServerAsyncTask<HttpResponse<LoginResponse>>(type) {
                    @Override
                    public void OnResponse(HttpResponse<LoginResponse> httpResponse) {
                        hideRequestDialog();
                        if (httpResponse.noErrorMessage()) {
                            saveToken(httpResponse.getResponseParams().getToken());
                            saveUserPhone(userName);
                            savePwd(password);
                            finish();
                        } else
                            showText(httpResponse.getError().getMessage());
                    }
                };
        task.sendRequest(httpRequest, false);
    }
}
