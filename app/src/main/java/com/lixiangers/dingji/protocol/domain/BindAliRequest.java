package com.lixiangers.dingji.protocol.domain;


import com.lixiangers.dingji.protocol.http.HttpRequestParams;

public class BindAliRequest extends HttpRequestParams {
    private String alipay_id;

    public BindAliRequest(String account) {
        this.alipay_id = account;
    }
}
