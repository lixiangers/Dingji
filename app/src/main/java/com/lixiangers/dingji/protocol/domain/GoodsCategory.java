package com.lixiangers.dingji.protocol.domain;


import com.lixiangers.dingji.model.Goods;

import java.util.List;

public class GoodsCategory {
    private String category;
    private List<Goods> data;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<Goods> getData() {
        return data;
    }

    public void setData(List<Goods> data) {
        this.data = data;
    }
}
