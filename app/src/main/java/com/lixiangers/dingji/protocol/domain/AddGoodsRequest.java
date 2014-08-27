package com.lixiangers.dingji.protocol.domain;

import com.lixiangers.dingji.protocol.http.HttpRequestParams;

public class AddGoodsRequest extends HttpRequestParams {
    private String name;
    private String unit;
    private int price;
    private String main_img;
    private String[] img;
    private String category;
    private String detail;

    public AddGoodsRequest(String name, String unit, int price, String main_img, String[] img, String category, String detail) {
        this.name = name;
        this.unit = unit;
        this.price = price;
        this.main_img = main_img;
        this.img = img;
        this.category = category;
        this.detail = detail;
    }
}
