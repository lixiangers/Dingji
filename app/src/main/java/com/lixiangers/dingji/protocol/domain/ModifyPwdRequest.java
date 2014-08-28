package com.lixiangers.dingji.protocol.domain;

import com.lixiangers.dingji.protocol.http.HttpRequestParams;

public class ModifyPwdRequest extends HttpRequestParams {
    private String new_passwd;
    private String old_passwd;

    public ModifyPwdRequest(String new_passwd, String old_passwd) {
        this.new_passwd = new_passwd;
        this.old_passwd = old_passwd;
    }
}
