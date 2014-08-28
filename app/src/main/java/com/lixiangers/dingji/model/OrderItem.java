package com.lixiangers.dingji.model;

import com.lixiangers.dingji.view.SelectQuantityView;

public class OrderItem {
    private Goods goods;
    private int quantity;

    private SelectQuantityView.onNumberChangeListener onNumberChangeListener;

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
}
