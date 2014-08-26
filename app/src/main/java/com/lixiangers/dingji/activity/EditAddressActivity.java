package com.lixiangers.dingji.activity;

import android.os.Bundle;

import com.lixiangers.dingji.R;
import com.lixiangers.dingji.view.LocationPopupWindow;

public class EditAddressActivity extends NeolixNaviagationBaseActivity {

    private LocationPopupWindow locationView;

    public EditAddressActivity() {
        super(R.layout.activity_edit_address);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("新增地址");
        setRightText("保存", null);

        locationView = (LocationPopupWindow) findViewById(R.id.location_view);
        locationView.setLocation("天津", "天津", "不限");
    }
}
