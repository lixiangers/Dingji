package com.lixiangers.dingji.protocol.domain;

import java.io.Serializable;

public class Address implements Serializable {
    private String name;
    private String phone;
    private String detailAddress;
    private boolean isDefault;
    private String province;
    private String city;
    private String county;

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public void setDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCompleteAddress() {
        return String.format("%s%s%s%s", province, city, county, detailAddress);
    }
}
