package com.lixiangers.dingji.protocol.domain;

import com.lixiangers.dingji.protocol.http.HttpRequestParams;

import java.util.List;

public class SubmitOrderRequest extends HttpRequestParams {

    private Integer status;//订单状态:-1: '订单已取消', 0: '下单成功', 1: '开始配送', 2: '配送成功'
    private String id;//: 11,  订单id, 仅用作接口的订单标识
    private String order_no;//: "14082622154830",  订单号码, 用于界面显示

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getReceiver_name() {
        return receiver_name;
    }

    public String getReceiver_mobile() {
        return receiver_mobile;
    }

    public String getReceiver_province() {
        return receiver_province;
    }

    public String getReceiver_city() {
        return receiver_city;
    }

    public String getReceiver_district() {
        return receiver_district;
    }

    public String getReceiver_detail_address() {
        return receiver_detail_address;
    }

    public int getTotal_price() {
        return total_price;
    }

    public List<Product> getProducts() {
        return products;
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
