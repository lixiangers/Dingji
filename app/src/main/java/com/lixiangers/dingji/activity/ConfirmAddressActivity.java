package com.lixiangers.dingji.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.reflect.TypeToken;
import com.lixiangers.dingji.R;
import com.lixiangers.dingji.protocol.domain.Address;
import com.lixiangers.dingji.protocol.http.HttpRequest;
import com.lixiangers.dingji.protocol.http.HttpResponse;
import com.lixiangers.dingji.protocol.http.RequestServerAsyncTask;
import com.lixiangers.dingji.protocol.http.RequestType;
import com.lixiangers.dingji.util.Constant;
import com.lixiangers.dingji.view.LocationPopupWindow;

import java.lang.reflect.Type;

import static com.lixiangers.dingji.util.DialogFactory.hideRequestDialog;
import static com.lixiangers.dingji.util.DialogFactory.showRequestDialog;
import static com.lixiangers.dingji.util.StringUtil.showText;

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
        locationView.setLocation(address.getProvince(), address.getCity(), address.getDistrict());
        contactEditView.setText(address.getName());
        phoneEditView.setText(address.getPhone());
        cityAreaEditView.setText(address.getProvince() + address.getCity() + address.getDistrict());
        detailEditView.setText(address.getDetail_address());
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
        showRequestDialog(this, getString(R.string.is_get_default_address));
        final HttpRequest httpRequest = new HttpRequest(
                RequestType.get_default_address, null);

        Type type = new TypeToken<HttpResponse<Address>>() {
        }.getType();

        RequestServerAsyncTask<HttpResponse<Address>> task =
                new RequestServerAsyncTask<HttpResponse<Address>>(type) {
                    @Override
                    public void OnResponse(HttpResponse<Address> httpResponse) {
                        hideRequestDialog();
                        if (httpResponse.noErrorMessage()) {
                            Address address1 = httpResponse.getResponseParams();
                            if (address1 != null)
                                fillData(address1);
                        } else
                            showText(httpResponse.getError().getMessage());
                    }
                };
        task.sendRequest(httpRequest, true);
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
