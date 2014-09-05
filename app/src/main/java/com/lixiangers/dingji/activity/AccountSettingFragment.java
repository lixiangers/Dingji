package com.lixiangers.dingji.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.lixiangers.dingji.R;
import com.lixiangers.dingji.protocol.http.HttpRequest;
import com.lixiangers.dingji.protocol.http.HttpResponse;
import com.lixiangers.dingji.protocol.http.RequestServerAsyncTask;
import com.lixiangers.dingji.protocol.http.RequestType;
import com.lixiangers.dingji.util.SharedPreferencesUtil;
import com.lixiangers.dingji.view.NavigationBar;

import java.lang.reflect.Type;

import static com.lixiangers.dingji.application.MyApplication.getInstance;
import static com.lixiangers.dingji.util.DialogFactory.hideRequestDialog;
import static com.lixiangers.dingji.util.DialogFactory.showRequestDialog;
import static com.lixiangers.dingji.util.StringUtil.showText;

public class AccountSettingFragment extends android.support.v4.app.Fragment {

    private TextView accountTextView;
    private View historyOrderView;
    private View manangerAddressView;
    private View aboutView;
    private View suggestView;
    private View problemView;
    private View modifyPasswordView;
    private View logoutButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_setting, container, false);
        NavigationBar navigationBar = (NavigationBar) view.findViewById(R.id.navigation_bar);
        navigationBar.setTitle(R.string.account_manager);

        accountTextView = (TextView) view.findViewById(R.id.tv_account);
        historyOrderView = view.findViewById(R.id.view_history_order);
        manangerAddressView = view.findViewById(R.id.view_manager_address);
        aboutView = view.findViewById(R.id.view_about);
        suggestView = view.findViewById(R.id.view_suggest);
        problemView = view.findViewById(R.id.view_problem);
        modifyPasswordView = view.findViewById(R.id.view_modify_password);
        logoutButton = view.findViewById(R.id.bt_logout);

        accountTextView.setText(SharedPreferencesUtil.getUserPhone());

        initListener();

        return view;
    }

    private void initListener() {
        manangerAddressView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getInstance(), ManagerAddressActivity.class));
            }
        });

        modifyPasswordView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getInstance(), ModifyPwdActivity.class));
            }
        });

        suggestView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getInstance(), SuggestActivity.class));
            }
        });

        aboutView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getInstance(), AboutActivity.class));
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        historyOrderView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getInstance(), QueryOrderActivity.class));
            }
        });

        problemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getInstance(), ProblemActivity.class));
            }
        });
    }

    private void logout() {
        final HttpRequest httpRequest = new HttpRequest(
                RequestType.user_logout, null);

        Type type = new TypeToken<HttpResponse<String>>() {
        }.getType();

        showRequestDialog(getActivity(), getInstance().getString(R.string.is_logout));
        RequestServerAsyncTask<HttpResponse<String>> task =
                new RequestServerAsyncTask<HttpResponse<String>>(type) {
                    @Override
                    public void OnResponse(HttpResponse<String> httpResponse) {
                        hideRequestDialog();
                        if (httpResponse.noErrorMessage()) {
                            getInstance().logoutApp();
                            startActivity(new Intent(getInstance(), LoginActivity.class));
                        } else
                            showText(httpResponse.getError().getMessage());
                    }
                };
        task.sendRequest(httpRequest, true);
    }
}
