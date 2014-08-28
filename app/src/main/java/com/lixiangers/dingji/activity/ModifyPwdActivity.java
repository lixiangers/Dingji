package com.lixiangers.dingji.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.gson.reflect.TypeToken;
import com.lixiangers.dingji.R;
import com.lixiangers.dingji.protocol.domain.ModifyPwdRequest;
import com.lixiangers.dingji.protocol.http.HttpRequest;
import com.lixiangers.dingji.protocol.http.HttpResponse;
import com.lixiangers.dingji.protocol.http.RequestServerAsyncTask;
import com.lixiangers.dingji.protocol.http.RequestType;

import java.lang.reflect.Type;

import static com.lixiangers.dingji.util.DialogFactory.hideRequestDialog;
import static com.lixiangers.dingji.util.DialogFactory.showRequestDialog;
import static com.lixiangers.dingji.util.StringUtil.getTextFrom;
import static com.lixiangers.dingji.util.StringUtil.isBlank;
import static com.lixiangers.dingji.util.StringUtil.showText;

public class ModifyPwdActivity extends NeolixNaviagationBaseActivity {

    private EditText oldPwdEditText;
    private EditText pwdEditText;
    private EditText confirmPwdEditText;

    public ModifyPwdActivity() {
        super(R.layout.activity_modify_pwd);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.modify_pwd));
        setRightText(R.string.save, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPwd = getTextFrom(oldPwdEditText);
                String pwd = getTextFrom(pwdEditText);
                String confirmPwd = getTextFrom(confirmPwdEditText);

                if (isBlank(oldPwd) || isBlank(pwd)) {
                    showText(R.string.data_incomplete);
                    return;
                }

                if (!confirmPwd.equals(pwd)) {
                    showText(getString(R.string.the_two_passwords_you_entered_do_not_match));
                    confirmPwdEditText.requestFocus();
                    return;
                }

                modifyPwd(oldPwd, pwd);
            }
        });

        initView();
    }

    private void modifyPwd(String oldPwd, String pwd) {
        ModifyPwdRequest params = new ModifyPwdRequest(pwd, oldPwd);
        final HttpRequest httpRequest = new HttpRequest(
                RequestType.change_passwd, params);

        Type type = new TypeToken<HttpResponse<String>>() {
        }.getType();

        showRequestDialog(ModifyPwdActivity.this, getString(R.string.is_modify_pwd));
        RequestServerAsyncTask<HttpResponse<String>> task =
                new RequestServerAsyncTask<HttpResponse<String>>(type) {
                    @Override
                    public void OnResponse(HttpResponse<String> httpResponse) {
                        hideRequestDialog();
                        if (httpResponse.noErrorMessage()) {
                            showText(getString(R.string.modify_pwd_success));
                            finish();
                        } else
                            showText(httpResponse.getError().getMessage());
                    }
                };
        task.sendRequest(httpRequest, true);
    }

    private void initView() {
        oldPwdEditText = (EditText) findViewById(R.id.et_old_pwd);
        pwdEditText = (EditText) findViewById(R.id.et_pwd);
        confirmPwdEditText = (EditText) findViewById(R.id.et_confirm_pwd);
    }
}
