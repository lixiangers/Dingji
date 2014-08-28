package com.lixiangers.dingji.protocol.domain;

import com.lixiangers.dingji.protocol.http.HttpRequestParams;

import java.io.Serializable;

public class AddressRequestAndReponse extends HttpRequestParams implements Serializable {
    private String id;
    private String address_id;
    private String name;
    private String mobile;
    private String detail_address;
    private String province;
    private String city;
    private String district;

    public String getAddress_id() {
        return address_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAddress_id(String address_id) {
        this.address_id = address_id;
    }


    public String getName() {
        return name;
    }

    public String getPhone() {
        return mobile;
    }

    public String getDetail_address() {
        return detail_address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.mobile = phone;
    }

    public void setDetail_address(String detail_address) {
        this.detail_address = detail_address;
    }

    public void setDefault(boolean isDefault) {
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

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCompleteAddress() {
        return String.format("%s%s%s%s", province, city, district, detail_address);
    }
}
