package com.qh.sso.Enum;

public enum TokenType {
    Password(1, "密码登陆"),
    PhoneVerificationCode(2, "手机验证码");

    private int code;
    private String msg;

    TokenType(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public static TokenType getByCode(int code) {
        for (TokenType e : TokenType.values()) {
            if (e.getCode() == code) {
                return e;
            }
        }
        return null;
    }
}
