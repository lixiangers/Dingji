package com.lixiangers.dingji.protocol.domain;

public class EditGoodsRequest extends AddGoodsRequest {
    private String product_id;

    public EditGoodsRequest(String name, String unit, int price, String main_img, String[] img, String category, String detail) {
        super(name, unit, price, main_img, img, category, detail);
    }

    public EditGoodsRequest(String id, String name, String unit, int price, String main_img, String[] img, String category, String detail) {
        super(name, unit, price, main_img, img, category, detail);
        this.product_id = id;
    }
}
