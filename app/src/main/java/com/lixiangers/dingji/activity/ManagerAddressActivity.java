package com.lixiangers.dingji.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.lixiangers.dingji.R;
import com.lixiangers.dingji.adapter.ModelListAdapter;
import com.lixiangers.dingji.protocol.domain.Address;
import com.lixiangers.dingji.util.Constant;

import java.util.ArrayList;
import java.util.List;

public class ManagerAddressActivity extends NeolixNaviagationBaseActivity {
    public static final int REQUEST_CODE_ADD_ADDRESS = 1;
    private ModelListAdapter<Address> adapter;
    private ListView addressListView;
    private Button addAddressButton;
    private List<Address> addressList;

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
        }
    }

    private void initListView() {
        adapter = new ModelListAdapter<Address>(getApplicationContext());
        addressListView.setAdapter(adapter);

        addressListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO
            }
        });
    }

    private void loadData() {
        //TODO get address list
    }
}
