package com.lixiangers.dingji.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.lixiangers.dingji.R;
import com.lixiangers.dingji.protocol.domain.Address;
import com.lixiangers.dingji.util.Constant;
import com.lixiangers.dingji.view.LocationPopupWindow;

public class ConfirmAddressActivity extends NeolixNaviagationBaseActivity {
    public static final int REQUEST_CODE_SELECT_ADDRESS = 1;
    private LocationPopupWindow locationView;
    private EditText cityAreaEditView;
    private EditText detailEditView;
    private EditText contactEditView;
    private EditText phoneEditView;
    private Button submitOrderButton;
    private View otherAddressView;

    public ConfirmAddressActivity() {
        super(R.layout.activity_confirm_address);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.confirm_order));

        initView();
        initListener();

        getDefaultAddress();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK)
            return;

        if (requestCode == REQUEST_CODE_SELECT_ADDRESS) {
            Address extra = (Address) data.getSerializableExtra(Constant.ADDRESS);
            fillData(extra);
        }
    }

    private void fillData(Address address) {
        locationView.setLocation(address.getProvince(), address.getCity(), address.getCounty());
        contactEditView.setText(address.getName());
        phoneEditView.setText(address.getPhone());
        cityAreaEditView.setText(address.getProvince() + address.getCity() + address.getCounty());
        detailEditView.setText(address.getDetailAddress());
    }

    private void initView() {
        contactEditView = (EditText) findViewById(R.id.et_contact);
        phoneEditView = (EditText) findViewById(R.id.et_phone);
        cityAreaEditView = (EditText) findViewById(R.id.et_city_area);
        detailEditView = (EditText) findViewById(R.id.et_detail_area);
        locationView = (LocationPopupWindow) findViewById(R.id.location_view);

        submitOrderButton = (Button) findViewById(R.id.bt_submit_order);
        otherAddressView = findViewById(R.id.tv_other_address);
    }

    private void getDefaultAddress() {
        //TODO get default address
//        fillData();
    }

    private void initListener() {
        locationView.setAreaChangeListener(new LocationPopupWindow.onAreaChangeListener() {
            @Override
            public void areaChange(String provinceString, String cityString, String countyString) {
                cityAreaEditView.setText(provinceString + cityString + countyString);
            }
        });

        submitOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO submit order
            }
        });

        otherAddressView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ManagerAddressActivity.class);
                intent.putExtra(Constant.IS_SELECT_ADDRESS, true);
                startActivityForResult(intent, REQUEST_CODE_SELECT_ADDRESS);
            }
        });
    }
}
