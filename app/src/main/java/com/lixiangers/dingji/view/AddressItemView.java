package com.lixiangers.dingji.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.lixiangers.dingji.R;
import com.lixiangers.dingji.adapter.BaseItemView;
import com.lixiangers.dingji.protocol.domain.Address;

public class AddressItemView extends BaseItemView<Address> {
    private final Context context;
    private TextView nameTextView;
    private TextView addressTextView;
    private TextView phoneTextView;
    private View backgroundView;

    public AddressItemView(Context context) {
        super(context);
        this.context = context;
        initUi();
    }

    public AddressItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initUi();
    }

    @Override
    public void setModel(Address model) {
        nameTextView.setText(model.getName());
        addressTextView.setText(model.getCompleteAddress());
        phoneTextView.setText(model.getPhone());

        backgroundView.setActivated(model.isDefault());
    }

    private void initUi() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_address, this);

        backgroundView = findViewById(R.id.view_background);

        nameTextView = (TextView) findViewById(R.id.tv_name);
        addressTextView = (TextView) findViewById(R.id.tv_address);
        phoneTextView = (TextView) findViewById(R.id.tv_phone);
    }
}
