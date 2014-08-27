package com.lixiangers.dingji.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lixiangers.dingji.R;
import com.lixiangers.dingji.view.NavigationBar;

import static com.lixiangers.dingji.application.MyApplication.getInstance;

public class AccountSettingFragment extends android.support.v4.app.Fragment {

    private TextView accountTextView;
    private View historyOrderView;
    private View manangerAddressView;
    private View aboutView;
    private View suggestView;
    private View problemView;
    private View modifyPasswordView;

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

        return view;
    }
}
