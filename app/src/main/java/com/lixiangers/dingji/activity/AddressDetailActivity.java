package com.lixiangers.dingji.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lixiangers.dingji.R;
import com.lixiangers.dingji.protocol.domain.Address;
import com.lixiangers.dingji.util.Constant;

public class AddressDetailActivity extends NeolixNaviagationBaseActivity {

    public static final int REQUEST_CODE_EDIT_ADDRESS = 1;
    private Address address;
    private TextView contactTextView;
    private TextView phoneTextView;
    private TextView detailTextView;
    private Button deleteAddressButton;
    private Button setDefaultButton;

    public AddressDetailActivity() {
        super(R.layout.activity_address_detail);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.address_detail));

        setRightText(getString(R.string.modify), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditAddressActivity.class);
                intent.putExtra(Constant.ADDRESS, address);
                startActivityForResult(intent, REQUEST_CODE_EDIT_ADDRESS);
            }
        });

        initView();
        initData();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK)
            return;

        if (requestCode == REQUEST_CODE_EDIT_ADDRESS) {
            setResult(RESULT_OK, data);
            finish();
        }
    }

    private void initView() {
        contactTextView = (TextView) findViewById(R.id.tv_contact);
        phoneTextView = (TextView) findViewById(R.id.tv_phone);
        detailTextView = (TextView) findViewById(R.id.tv_detail_area);

        deleteAddressButton = (Button) findViewById(R.id.bt_delete_address);
        setDefaultButton = (Button) findViewById(R.id.bt_set_default);

        deleteAddressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(Constant.IS_DELETE, true);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        setDefaultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO set default address

                address.setDefault(true);
                Intent intent = new Intent();
                intent.putExtra(Constant.ADDRESS, address);
                intent.putExtra(Constant.IS_CHANGE_DEFAULT_ADDRESS, true);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private void initData() {
        address = (Address) getIntent().getSerializableExtra(Constant.ADDRESS);
        contactTextView.setText(address.getName());
        phoneTextView.setText(address.getPhone());
        detailTextView.setText(address.getCompleteAddress());

        setDefaultButton.setVisibility(address.isDefault() ? View.GONE : View.VISIBLE);
    }
}
