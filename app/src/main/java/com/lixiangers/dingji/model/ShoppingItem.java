package com.lixiangers.dingji.model;

import android.view.View;

import com.lixiangers.dingji.view.SelectQuantityView;

public class ShoppingItem {
    private Goods goods;
    private int quantity;

    private SelectQuantityView.onNumberChangeListener onNumberChangeListener;
    private View.OnLongClickListener onLongClickListener;

    public Goods getGoods() {
        return goods;
    }


    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setOnNumberChangeListener(SelectQuantityView.onNumberChangeListener onNumberChangeListener) {
        this.onNumberChangeListener = onNumberChangeListener;
    }

    public SelectQuantityView.onNumberChangeListener getOnNumberChangeListener() {
        return onNumberChangeListener;
    }

    public int getTotalAmount() {
        return goods.getPrice() * quantity;
    }

    public View.OnLongClickListener getOnLongClickListener() {
        return onLongClickListener;
    }

    public void setOnLongClickListener(View.OnLongClickListener onLongClickListener) {
        this.onLongClickListener = onLongClickListener;
    }
}
