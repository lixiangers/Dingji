package com.lixiangers.dingji.protocol.domain;

import com.lixiangers.dingji.protocol.http.HttpRequestParams;

import java.util.List;

public class SubmitOrderRequest extends HttpRequestParams {

    private String receiver_name;//: 收货人姓名
    private String receiver_mobile;//: 收货人手机
    private String receiver_province;//: 收货人省份
    private String receiver_city;//,收货人市辖区
    private String receiver_district;//'', 收货人地区
    private String receiver_detail_address;//, 收货人详细地址
    private int total_price;//,  int 总价格, 以分为单位
    private List<Product> products;//

    public void setReceiver_name(String receiver_name) {
        this.receiver_name = receiver_name;
    }

    public void setReceiver_mobile(String receiver_mobile) {
        this.receiver_mobile = receiver_mobile;
    }

    public void setReceiver_province(String receiver_province) {
        this.receiver_province = receiver_province;
    }

    public void setReceiver_city(String receiver_city) {
        this.receiver_city = receiver_city;
    }

    public void setReceiver_district(String receiver_district) {
        this.receiver_district = receiver_district;
    }

    public void setReceiver_detail_address(String receiver_detail_address) {
        this.receiver_detail_address = receiver_detail_address;
    }

    public void setTotal_price(int total_price) {
        this.total_price = total_price;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public static class Product {
        int count;
        String product_id;

        public void setCount(int count) {
            this.count = count;
        }

        public void setProduct_id(String product_id) {
            this.product_id = product_id;
        }
    }
}
