package com.lixiangers.dingji.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.lixiangers.dingji.R;
import com.lixiangers.dingji.adapter.ModelListAdapter;
import com.lixiangers.dingji.protocol.domain.Address;

public class ManagerAddressActivity extends NeolixNaviagationBaseActivity {
    private ModelListAdapter<Address> adapter;
    private ListView addressListView;

    public ManagerAddressActivity() {
        super(R.layout.activity_manager_address);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.manager_address));

        addressListView = (ListView) findViewById(R.id.lv_address);

        adapter = new ModelListAdapter<Address>(getApplicationContext());
        addressListView.setAdapter(adapter);

        loadData();
    }

    private void loadData() {
        //TODO get address list
    }
}
