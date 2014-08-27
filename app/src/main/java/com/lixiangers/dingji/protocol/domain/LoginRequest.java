package com.lixiangers.dingji.protocol.domain;

import com.lixiangers.dingji.protocol.http.HttpRequestParams;

public class LoginRequest extends HttpRequestParams {
    private String username;
    private String password;

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
