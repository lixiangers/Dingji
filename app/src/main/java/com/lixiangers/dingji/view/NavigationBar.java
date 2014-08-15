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
    private ImageView rightButton;
    private TextView activityTitle;
    private ImageView leftButton;
    private ImageView centerImageView;

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

        rightButton = (ImageView) findViewById(R.id.right_button);
        leftButton = (ImageView) findViewById(R.id.left_button);
        activityTitle = (TextView) findViewById(R.id.activity_title);
        centerImageView = (ImageView) findViewById(R.id.center_image_view);
    }

    public void setTitle(CharSequence text) {
        activityTitle.setVisibility(VISIBLE);
        activityTitle.setText(text);
        centerImageView.setVisibility(GONE);
        leftButton.setVisibility(GONE);
        rightButton.setVisibility(GONE);
    }

    public void setTitle(int resId) {
        activityTitle.setVisibility(VISIBLE);
        activityTitle.setText(resId);
        centerImageView.setVisibility(GONE);
        leftButton.setVisibility(GONE);
        rightButton.setVisibility(GONE);
    }

    public void setRightButton(int resId) {
        rightButton.setBackgroundResource(resId);
        rightButton.setVisibility(VISIBLE);
    }

    public void setRightButtonClickedListener(OnClickListener listener) {
        rightButton.setOnClickListener(listener);
    }

    public void setLeftButton(int resId) {
        leftButton.setImageResource(resId);
        leftButton.setVisibility(VISIBLE);
    }

    public void setLeftButtonClickedListener(OnClickListener listener) {
        leftButton.setOnClickListener(listener);
    }

    public void setLeftButtonVisibility(boolean isDisplay) {
        leftButton.setVisibility(isDisplay ? VISIBLE : INVISIBLE);
    }

    public void setRightButtonVisibility(boolean isDisplay) {
        rightButton.setVisibility(isDisplay ? VISIBLE : INVISIBLE);
    }

    public void setCenterImageResource(int resource) {
        centerImageView.setVisibility(VISIBLE);
        centerImageView.setImageResource(resource);
        activityTitle.setVisibility(GONE);
        leftButton.setVisibility(GONE);
        rightButton.setVisibility(GONE);
    }
}
