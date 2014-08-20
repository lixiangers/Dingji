package com.lixiangers.dingji.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.lixiangers.dingji.R;

public class SelectQuantityView extends LinearLayout {
    private Context context;
    private EditText quantityEditView;

    public SelectQuantityView(Context context) {
        super(context);
        this.context = context;
        initUi();
    }

    public SelectQuantityView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initUi();
    }

    public SelectQuantityView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        initUi();
    }

    private void initUi() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_select_quantity, this);

        quantityEditView = (EditText) findViewById(R.id.et_quantity);
    }
}
