package com.lixiangers.dingji.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.reflect.TypeToken;
import com.lixiangers.dingji.R;
import com.lixiangers.dingji.adapter.ModelListAdapter;
import com.lixiangers.dingji.protocol.domain.Address;
import com.lixiangers.dingji.protocol.domain.AddressListReponse;
import com.lixiangers.dingji.protocol.http.HttpRequest;
import com.lixiangers.dingji.protocol.http.HttpResponse;
import com.lixiangers.dingji.protocol.http.RequestServerAsyncTask;
import com.lixiangers.dingji.protocol.http.RequestType;
import com.lixiangers.dingji.util.Constant;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.lixiangers.dingji.util.DialogFactory.hideRequestDialog;
import static com.lixiangers.dingji.util.DialogFactory.showRequestDialog;
import static com.lixiangers.dingji.util.StringUtil.showText;

public class ManagerAddressActivity extends NeolixNaviagationBaseActivity {
    public static final int REQUEST_CODE_ADD_ADDRESS = 1;
    public static final int REQUEST_CODE_EDIT_ADDRESS = 2;
    private ModelListAdapter<Address> adapter;
    private ListView addressListView;
    private Button addAddressButton;
    private List<Address> addressList;
    private int currentIndex;
    private boolean isSelectAddress;

    public ManagerAddressActivity() {
        super(R.layout.activity_manager_address);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.manager_address));

        addressListView = (ListView) findViewById(R.id.lv_address);
        addAddressButton = (Button) findViewById(R.id.bt_add);

        initListView();
        addAddressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditAddressActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD_ADDRESS);
            }
        });

        addressList = new ArrayList<Address>();
        isSelectAddress = getIntent().getBooleanExtra(Constant.IS_SELECT_ADDRESS, false);
        loadData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK)
            return;

        if (requestCode == REQUEST_CODE_ADD_ADDRESS) {
            Address address = (Address) data.getSerializableExtra(Constant.ADDRESS);
            addressList.add(address);
            adapter.setData(addressList);
        } else if (requestCode == REQUEST_CODE_EDIT_ADDRESS) {
            boolean isDelete = data.getBooleanExtra(Constant.IS_DELETE, false);
            if (isDelete) {
                addressList.remove(currentIndex);
            } else {
                boolean isChangeDefault = data.getBooleanExtra(Constant.IS_CHANGE_DEFAULT_ADDRESS, false);
                Address address = (Address) data.getSerializableExtra(Constant.ADDRESS);
                addressList.set(currentIndex, address);
                if (isChangeDefault) {
                    changeDefaultAddrss(address);
                }
            }

            adapter.setData(addressList);
        }
    }

    private void changeDefaultAddrss(Address address) {
        for (Address address1 : addressList) {
            if (address1.isDefault() && address1 != address) {
                address1.setDefault(false);
                break;
            }
        }
    }

    private void initListView() {
        adapter = new ModelListAdapter<Address>(getApplicationContext());
        addressListView.setAdapter(adapter);

        addressListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (isSelectAddress) {
                    Intent intent = new Intent();
                    intent.putExtra(Constant.ADDRESS, adapter.getItem(position));
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    currentIndex = position;
                    Intent intent = new Intent(getApplicationContext(), AddressDetailActivity.class);
                    intent.putExtra(Constant.ADDRESS, adapter.getItem(position));
                    startActivityForResult(intent, REQUEST_CODE_EDIT_ADDRESS);
                }
            }
        });
    }

    private void loadData() {
        queryAddress();
    }

    private void queryAddress() {
        showRequestDialog(ManagerAddressActivity.this, getString(R.string.is_get_addrss));
        final HttpRequest httpRequest = new HttpRequest(
                RequestType.list_address, null);

        Type type = new TypeToken<HttpResponse<AddressListReponse>>() {
        }.getType();

        RequestServerAsyncTask<HttpResponse<AddressListReponse>> task =
                new RequestServerAsyncTask<HttpResponse<AddressListReponse>>(type) {
                    @Override
                    public void OnResponse(HttpResponse<AddressListReponse> httpResponse) {
                        hideRequestDialog();
                        if (httpResponse.noErrorMessage()) {
                            addressList = httpResponse.getResponseParams().getAddress_list();
                            String defaultAddressId = httpResponse.getResponseParams().getDefault_address_id();
                            for (Address address : addressList) {
                                if (address.getId().equals(defaultAddressId)) {
                                    address.setDefault(true);
                                    break;
                                }
                            }
                            adapter.setData(addressList);
                        } else
                            showText(httpResponse.getError().getMessage());
                    }
                };
        task.sendRequest(httpRequest, true);
    }
}
