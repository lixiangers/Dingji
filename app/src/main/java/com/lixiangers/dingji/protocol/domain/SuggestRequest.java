package com.lixiangers.dingji.protocol.domain;

import com.lixiangers.dingji.protocol.http.HttpRequestParams;

public class SuggestRequest extends HttpRequestParams {
    private String content;

    public SuggestRequest(String content) {
        this.content = content;
    }
}
