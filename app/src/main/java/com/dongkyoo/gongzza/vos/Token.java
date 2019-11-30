package com.dongkyoo.gongzza.vos;

import java.util.Date;

public class Token {

    private String userId;
    private String token;
    private Date lastUsedAt;

    public Token() {
    }

    public Token(String userId, String token, Date lastUsedAt) {
        this.userId = userId;
        this.token = token;
        this.lastUsedAt = lastUsedAt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getLastUsedAt() {
        return lastUsedAt;
    }

    public void setLastUsedAt(Date lastUsedAt) {
        this.lastUsedAt = lastUsedAt;
    }
}
