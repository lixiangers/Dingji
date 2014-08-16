package com.lixiangers.dingji.protocol.http;


import com.lixiangers.dingji.util.Constant;

import static com.lixiangers.dingji.util.StringUtil.generateRandomCode;

public class HttpRequest {
    protected String jsonrpc;
    protected RequestType method;
    protected String id;
    protected HttpRequestParams params;

    public HttpRequest(RequestType method, HttpRequestParams params) {
        this.jsonrpc = Constant.HttpConstant.JSON_RPC_VERSION;
        this.method = method;
        this.id = generateRandomCode(6);
        this.params = params;
    }
}
