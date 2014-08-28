package com.lixiangers.dingji.protocol.domain;

import com.lixiangers.dingji.protocol.http.HttpRequestParams;

public class DeleteAddressRequset extends HttpRequestParams {


    private String address_id;

    public DeleteAddressRequset(String address_id) {
        this.address_id = address_id;
    }
}
