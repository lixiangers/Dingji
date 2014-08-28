package com.lixiangers.dingji.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.gson.reflect.TypeToken;
import com.lixiangers.dingji.R;
import com.lixiangers.dingji.protocol.domain.Address;
import com.lixiangers.dingji.protocol.domain.AddressRequestAndReponse;
import com.lixiangers.dingji.protocol.http.HttpRequest;
import com.lixiangers.dingji.protocol.http.HttpResponse;
import com.lixiangers.dingji.protocol.http.RequestServerAsyncTask;
import com.lixiangers.dingji.protocol.http.RequestType;
import com.lixiangers.dingji.util.Constant;
import com.lixiangers.dingji.view.LocationPopupWindow;

import java.lang.reflect.Type;

import static com.lixiangers.dingji.util.DialogFactory.hideRequestDialog;
import static com.lixiangers.dingji.util.DialogFactory.showRequestDialog;
import static com.lixiangers.dingji.util.StringUtil.getTextFrom;
import static com.lixiangers.dingji.util.StringUtil.isBlank;
import static com.lixiangers.dingji.util.StringUtil.showText;

public class EditAddressActivity extends NeolixNaviagationBaseActivity {

    private LocationPopupWindow locationView;
    private EditText cityAreaEditView;
    private EditText detailEditView;
    private EditText contactEditView;
    private EditText phoneEditView;
    private Address address;
    private String province;
    private String city;
    private String county;

    public EditAddressActivity() {
        super(R.layout.activity_edit_address);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        address = (Address) getIntent().getSerializableExtra(Constant.ADDRESS);
        if (address == null)
            setTitle(getString(R.string.add_address));
        else
            setTitle(R.string.modify_address);

        setRightText(R.string.save, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addOrModifyAddress();
            }
        });

        initView();
        initListener();

        if (address != null) {
            locationView.setLocation(address.getProvince(), address.getCity(), address.getDistrict());
            contactEditView.setText(address.getName());
            phoneEditView.setText(address.getPhone());
            cityAreaEditView.setText(address.getProvince() + address.getCity() + address.getDistrict());
            detailEditView.setText(address.getDetail_address());
        }
    }

    private void addOrModifyAddress() {
        String contact = getTextFrom(contactEditView);
        String phone = getTextFrom(phoneEditView);
        String cityArea = getTextFrom(cityAreaEditView);
        String detailArea = getTextFrom(detailEditView);
        boolean isAdd = false;

        if (isBlank(contact) || isBlank(phone) || isBlank(cityArea) || isBlank(detailArea)) {
            showText(getString(R.string.data_incomplete));
            return;
        }


        if (address == null) {
            isAdd = true;
            address = new Address();
        }

        address.setPhone(phone);
        address.setName(contact);
        address.setProvince(province);
        address.setCity(city);
        address.setDistrict(county);
        address.setDetail_address(detailArea);
        address.setDefault(isAdd ? false : address.isDefault());

        if (isAdd)
            addAddress(address);
        else
            modifyAddress(address);
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
                province = provinceString;
                city = cityString;
                county = countyString;
            }
        });
    }

    private void addAddress(final Address address1) {
        showRequestDialog(EditAddressActivity.this, getString(R.string.is_add_address));
        AddressRequestAndReponse addressRequestAndReponse = address1.getAddressRequestAndReponse();
        addressRequestAndReponse.setAddress_id(null);
        final HttpRequest httpRequest = new HttpRequest(
                RequestType.add_address, addressRequestAndReponse);

        Type type = new TypeToken<HttpResponse<Address>>() {
        }.getType();

        RequestServerAsyncTask<HttpResponse<Address>> task =
                new RequestServerAsyncTask<HttpResponse<Address>>(type) {
                    @Override
                    public void OnResponse(HttpResponse<Address> httpResponse) {
                        hideRequestDialog();
                        if (httpResponse.noErrorMessage()) {
                            address1.setId(httpResponse.getResponseParams().getId());
                            Intent intent = new Intent();
                            intent.putExtra(Constant.ADDRESS, address1);
                            setResult(RESULT_OK, intent);
                            finish();
                        } else
                            showText(httpResponse.getError().getMessage());
                    }
                };
        task.sendRequest(httpRequest, true);
    }

    private void modifyAddress(final Address address1) {
        showRequestDialog(EditAddressActivity.this, getString(R.string.is_modify_address));
        address1.setAddress_id(address1.getId());
        AddressRequestAndReponse addressRequestAndReponse = address1.getAddressRequestAndReponse();
        addressRequestAndReponse.setId(null);

        final HttpRequest httpRequest = new HttpRequest(
                RequestType.edit_address, addressRequestAndReponse);

        Type type = new TypeToken<HttpResponse<Address>>() {
        }.getType();

        RequestServerAsyncTask<HttpResponse<Address>> task =
                new RequestServerAsyncTask<HttpResponse<Address>>(type) {
                    @Override
                    public void OnResponse(HttpResponse<Address> httpResponse) {
                        hideRequestDialog();
                        if (httpResponse.noErrorMessage()) {
                            Intent intent = new Intent();
                            intent.putExtra(Constant.ADDRESS, address1);
                            setResult(RESULT_OK, intent);
                            finish();
                        } else
                            showText(httpResponse.getError().getMessage());
                    }
                };
        task.sendRequest(httpRequest, true);
    }
}
