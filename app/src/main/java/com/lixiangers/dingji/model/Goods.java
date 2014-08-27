package com.lixiangers.dingji.model;

import com.lixiangers.dingji.R;
import com.lixiangers.dingji.util.StringUtil;

import java.util.ArrayList;


public class Goods implements java.io.Serializable {

    private String product_id;
    private String name;
    private String unit;
    private Integer price;
    private String main_img;
    private ArrayList<String> img;
    private String category;
    private String detail;

    public String getMain_img() {
        return main_img;
    }

    public void setMain_img(String main_img) {
        this.main_img = main_img;
    }

    public Goods() {
    }

    public Goods(String id) {
        this.product_id = id;
    }

    public Goods(String id, String name, String des, String unit, Integer price, ArrayList<String> imageList, String category) {
        this.product_id = id;
        this.name = name;
        this.detail = des;
        this.unit = unit;
        this.price = price;
        this.img = imageList;
        this.category = category;
    }

    public String getid() {
        return product_id;
    }

    public void setid(String id) {
        this.product_id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public ArrayList<String> getImg() {
        return img;
    }

    public void setImg(ArrayList<String> img) {
        this.img = img;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    public float getPriceOfYuan() {
        return getPrice() / 100f;
    }

    public String getPriceAndUnit() {
        return StringUtil.formatTemplateString(R.string.price_unit,
                getPriceOfYuan(), getUnit());
    }
}
