package com.lixiangers.dingji.protocol.domain;


import com.lixiangers.dingji.protocol.http.HttpRequestParams;

public class UploadImageRequest extends HttpRequestParams {
    private String data;
    private String format = "jpg";

    public UploadImageRequest(String data) {
        this.data = data;
    }
}
