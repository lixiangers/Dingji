package com.lixiangers.dingji.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lixiangers.dingji.R;


public class OrderMoreItemView extends RelativeLayout implements Checkable {

    private ImageView ico;
    private TextView text;
    private boolean isChecked;
    private View container;
    private CheckBox checkedIco;

    public OrderMoreItemView(Context context) {
        super(context);
        initUi();
    }

    public OrderMoreItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initUi();
    }

    public OrderMoreItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initUi();
    }

    @Override
    public void setChecked(boolean checked) {
        if (checked) {
            checkedIco.setVisibility(View.VISIBLE);
            text.setTextColor(Color.RED);
        } else {
            checkedIco.setVisibility(View.INVISIBLE);
            text.setTextColor(Color.BLACK);
        }

        isChecked = checked;
    }

    @Override
    public boolean isChecked() {
        return isChecked;
    }

    @Override
    public void toggle() {
        isChecked = !isChecked;
    }

    public void setText(String displayName) {
        text.setText(displayName);
    }

    public void setIcon(Drawable drawable) {
        ico.setBackgroundDrawable(drawable);
    }

    private void initUi() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        container = inflater.inflate(R.layout.custom_radio_button, this, true);
        ico = (ImageView) container.findViewById(R.id.filter_option_ico);
        text = (TextView) container.findViewById(R.id.filter_option_text);
        checkedIco = (CheckBox) container.findViewById(R.id.filter_option_checked_ico);
    }
}
