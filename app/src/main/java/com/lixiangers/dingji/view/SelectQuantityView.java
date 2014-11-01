package com.lixiangers.dingji.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.lixiangers.dingji.R;
import com.lixiangers.dingji.util.LocalTextWatcher;
import com.lixiangers.dingji.util.StringUtil;

public class SelectQuantityView extends LinearLayout {
    private Context context;
    private EditText quantityEditView;
    private int quantity;
    private View subtractionButton;
    private View additionButton;

    private onNumberChangeListener onNumberChangeListener = new onNumberChangeListener() {
        @Override
        public void onNumberChange(int number) {

        }
    };

    public void setOnNumberChangeListener(SelectQuantityView.onNumberChangeListener onNumberChangeListener) {
        this.onNumberChangeListener = onNumberChangeListener;
    }

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

    public int getNumber() {
        return quantity;
    }

    public void setNumber(int value) {
        quantity = value;
        setQuantityTextValue();
    }

    private void initUi() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_select_quantity, this);

        quantityEditView = (EditText) findViewById(R.id.et_quantity);
        subtractionButton = findViewById(R.id.bt_subtraction);
        additionButton = findViewById(R.id.bt_addition);

        quantityEditView.addTextChangedListener(new LocalTextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                super.onTextChanged(charSequence, i, i2, i3);
                quantityEditView.requestFocus();
                if (StringUtil.isBlank(charSequence.toString())) {
                    quantity = 1;
                    onNumberChangeListener.onNumberChange(quantity);
                    return;
                }

                Integer integer = Integer.valueOf(charSequence.toString());
                if (integer < 1) {
                    quantity = 1;
                    setQuantityTextValue();
                } else {
                    quantity = integer;
                    onNumberChangeListener.onNumberChange(quantity);
                }
            }
        });
        subtractionButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity--;
                setQuantityTextValue();
            }
        });
        additionButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity++;
                setQuantityTextValue();
            }
        });

        quantity = 1;
        setQuantityTextValue();
    }

    private void setQuantityTextValue() {
        quantityEditView.setText(String.valueOf(quantity));
        quantityEditView.setSelection(quantityEditView.getText().toString().length());
    }

    public interface onNumberChangeListener {
        void onNumberChange(int number);
    }
}
