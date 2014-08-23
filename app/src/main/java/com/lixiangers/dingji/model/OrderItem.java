package com.lixiangers.dingji.model;

import com.lixiangers.dingji.dao.Goods;

public class OrderItem {
    private Goods goods;
    private int quantity;

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

    public float getTotalAmount() {
        return goods.getPriceOfYuan() * quantity;
    }
}
