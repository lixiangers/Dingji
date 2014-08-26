package com.lixiangers.dingji.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lixiangers.dingji.R;


public class NavigationBar extends LinearLayout {

    private Context context;
    private ImageView rightImage;
    private TextView activityTitle;
    private ImageView leftImage;
    private ImageView centerImageView;
    private TextView rightTextView;

    public NavigationBar(Context context) {
        super(context);
        this.context = context;
        initUI();
    }

    public NavigationBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initUI();
    }

    private void initUI() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_navigation_bar, this);

        rightImage = (ImageView) findViewById(R.id.right_button);
        leftImage = (ImageView) findViewById(R.id.left_button);
        activityTitle = (TextView) findViewById(R.id.activity_title);
        centerImageView = (ImageView) findViewById(R.id.center_image_view);

        rightTextView = (TextView) findViewById(R.id.right_text_view);
    }

    public void setTitle(CharSequence text) {
        activityTitle.setText(text);
        resetView();
    }

    public void setTitle(int resId) {
        activityTitle.setText(resId);
        resetView();
    }

    private void resetView() {
        activityTitle.setVisibility(VISIBLE);
        centerImageView.setVisibility(GONE);
        leftImage.setVisibility(GONE);
        rightImage.setVisibility(GONE);
        rightTextView.setVisibility(GONE);
    }

    public void setLeftImage(int resId, OnClickListener listener) {
        leftImage.setVisibility(VISIBLE);
        leftImage.setImageResource(resId);
        leftImage.setOnClickListener(listener);
    }

    public void setRightImage(int resId, OnClickListener listener) {
        setRightView(true);
        rightImage.setImageResource(resId);
        rightImage.setOnClickListener(listener);
    }

    public void setCenterImageResource(int resource) {
        centerImageView.setVisibility(VISIBLE);
        centerImageView.setImageResource(resource);
        activityTitle.setVisibility(GONE);
        leftImage.setVisibility(GONE);
        rightImage.setVisibility(GONE);
    }

    public void setRightText(CharSequence text, OnClickListener listener) {
        rightTextView.setText(text);
        setRightView(true);
        rightTextView.setOnClickListener(listener);
    }

    public void setRightText(int resourceId, OnClickListener listener) {
        rightTextView.setText(resourceId);
        setRightView(true);
        rightTextView.setOnClickListener(listener);
    }

    private void setRightView(boolean isShowText) {
        rightTextView.setVisibility(isShowText ? VISIBLE : GONE);
        rightImage.setVisibility(isShowText ? GONE : VISIBLE);
    }
}
