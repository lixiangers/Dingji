package com.lixiangers.dingji.viewmodel;

import android.view.View;

import com.lixiangers.dingji.model.Goods;

import java.io.Serializable;

public class GoodsItemViewModel implements Serializable {
    private Goods goods;
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    private boolean isShowByView = false;

    public boolean isShowByView() {
        return isShowByView;
    }

    public void setShowByView(boolean isShowByView) {
        this.isShowByView = isShowByView;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public View.OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}
