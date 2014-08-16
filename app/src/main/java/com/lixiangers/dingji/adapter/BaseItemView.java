package com.lixiangers.dingji.adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class BaseItemView<T> extends LinearLayout {
    public BaseItemView(Context context) {
        super(context);
    }

    public BaseItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setModel(T model) {
    }

    public void setIsSelected(boolean b) {
    }
}
