package com.lixiangers.dingji.protocol.domain;

import com.lixiangers.dingji.protocol.http.HttpRequestParams;

public class ChangeOrderStatusRequest extends HttpRequestParams {

    private String order_id;
    private int status;

    public ChangeOrderStatusRequest(String order_id, int status) {
        this.order_id = order_id;
        this.status = status;
    }
}
