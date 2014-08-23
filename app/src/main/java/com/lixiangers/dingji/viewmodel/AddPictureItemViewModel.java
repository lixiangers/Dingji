package com.lixiangers.dingji.viewmodel;

import static android.view.View.OnClickListener;

public class AddPictureItemViewModel {
    private String bitmapURL;
    private OnClickListener onDeleteListener;
    private OnClickListener onAddListener;
    private boolean isCanDelete = false;

    public OnClickListener getOnAddListener() {
        return onAddListener;
    }

    public void setOnAddListener(OnClickListener onAddListener) {
        this.onAddListener = onAddListener;
    }

    public String getBitmapURL() {
        return bitmapURL;
    }

    public void setBitmapURL(String bitmapURL) {
        this.bitmapURL = bitmapURL;
    }

    public OnClickListener getOnDeleteListener() {
        return onDeleteListener;
    }

    public void setOnDeleteListener(OnClickListener onDeleteListener) {
        this.onDeleteListener = onDeleteListener;
    }

    public boolean isCanDelete() {
        return isCanDelete;
    }

    public void setCanDelete(boolean isCanDelete) {
        this.isCanDelete = isCanDelete;
    }

}
