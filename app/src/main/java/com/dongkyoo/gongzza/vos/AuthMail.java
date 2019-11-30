package com.dongkyoo.gongzza.vos;

public class AuthMail {

    private String userId;
    private String email;
    private String code;

    public AuthMail() {
    }

    public AuthMail(String userId, String email, String code) throws Exception {
        this.userId = userId;
        this.email = email;
        this.code = code;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
