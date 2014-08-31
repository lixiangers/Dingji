package com.lixiangers.dingji.protocol.domain;

import com.lixiangers.dingji.protocol.http.HttpRequestParams;

public class QueryOrderDeTailRequest extends HttpRequestParams {
    private String order_id;

    public QueryOrderDeTailRequest(String order_id) {
        this.order_id = order_id;
    }
}
