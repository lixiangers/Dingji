package com.lixiangers.dingji.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.lixiangers.dingji.R;
import com.lixiangers.dingji.protocol.domain.Address;
import com.lixiangers.dingji.util.Constant;
import com.lixiangers.dingji.view.LocationPopupWindow;

import static com.lixiangers.dingji.util.StringUtil.getTextFrom;
import static com.lixiangers.dingji.util.StringUtil.isBlank;
import static com.lixiangers.dingji.util.StringUtil.showText;

public class EditAddressActivity extends NeolixNaviagationBaseActivity {

    private LocationPopupWindow locationView;
    private EditText cityAreaEditView;
    private EditText detailEditView;
    private EditText contactEditView;
    private EditText phoneEditView;

    public EditAddressActivity() {
        super(R.layout.activity_edit_address);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.add_address));
        setRightText(R.string.save, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String contact = getTextFrom(contactEditView);
                String phone = getTextFrom(phoneEditView);
                String cityArea = getTextFrom(cityAreaEditView);
                String detailArea = getTextFrom(detailEditView);

                if (isBlank(contact) || isBlank(phone) || isBlank(cityArea) || isBlank(detailArea)) {
                    showText(getString(R.string.data_incomplete));
                    return;
                }

                Address address = new Address();
                address.setPhone(phone);
                address.setName(contact);
                address.setAddress(cityArea + detailArea);
                address.setDefault(false);

                Intent intent = new Intent();
                intent.putExtra(Constant.ADDRESS, address);
                setResult(RESULT_OK, intent);

                finish();
            }
        });

        initView();
        initListener();

        locationView.setLocation("天津", "天津", "不限");
    }

    private void initView() {
        contactEditView = (EditText) findViewById(R.id.et_contact);
        phoneEditView = (EditText) findViewById(R.id.et_phone);
        cityAreaEditView = (EditText) findViewById(R.id.et_city_area);
        detailEditView = (EditText) findViewById(R.id.et_detail_area);
        locationView = (LocationPopupWindow) findViewById(R.id.location_view);
    }

    private void initListener() {
        locationView.setAreaChangeListener(new LocationPopupWindow.onAreaChangeListener() {
            @Override
            public void areaChange(String provinceString, String cityString, String countyString) {
                cityAreaEditView.setText(provinceString + cityString + countyString);
            }
        });
    }
}
