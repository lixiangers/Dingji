package com.lixiangers.dingji.protocol.domain;

import java.util.Date;

public class LoginResponse {
    private String token;
    private Date expire_time;
    private boolean is_superuser;

    public String getToken() {
        return token;
    }

    public Date getExpire_time() {
        return expire_time;
    }

    public boolean is_superuser() {
        return is_superuser;
    }
}
