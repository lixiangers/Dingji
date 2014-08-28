package com.lixiangers.dingji.protocol.domain;

import java.util.List;

public class AddressListReponse {
    private String default_address_id;
    private List<Address> address_list;

    public String getDefault_address_id() {
        return default_address_id;
    }

    public void setDefault_address_id(String default_address_id) {
        this.default_address_id = default_address_id;
    }

    public List<Address> getAddress_list() {
        return address_list;
    }

    public void setAddress_list(List<Address> address_list) {
        this.address_list = address_list;
    }
}
