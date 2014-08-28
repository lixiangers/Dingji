package com.lixiangers.dingji.protocol.domain;

import com.lixiangers.dingji.protocol.http.HttpRequestParams;

public class DeleteProductRequest extends HttpRequestParams {
    private String product_id;

    public DeleteProductRequest(String del_product) {
        this.product_id = del_product;
    }
}
