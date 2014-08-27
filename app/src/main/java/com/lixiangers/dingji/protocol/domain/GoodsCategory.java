package com.lixiangers.dingji.protocol.domain;


import com.lixiangers.dingji.model.Goods;

import java.util.List;

public class GoodsCategory {
    private String categoryName;
    private List<Goods> goodsList;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<Goods> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<Goods> goodsList) {
        this.goodsList = goodsList;
    }
}
