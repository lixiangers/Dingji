package com.lixiangers.dingji.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.lixiangers.dingji.R;
import com.lixiangers.dingji.adapter.ModelListAdapter;
import com.lixiangers.dingji.protocol.domain.Address;

public class ManagerAddressActivity extends NeolixNaviagationBaseActivity {
    private ModelListAdapter<Address> adapter;
    private ListView addressListView;
    private Button addAddressButton;

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
                //TODO
                goTo(EditAddressActivity.class);
            }
        });
        loadData();
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
