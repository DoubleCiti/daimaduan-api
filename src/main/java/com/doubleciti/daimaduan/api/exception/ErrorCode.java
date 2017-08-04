package com.doubleciti.daimaduan.api.exception;

public enum ErrorCode {
    DUPLICATE_USER_EMAIL(101);

    private int code;

    ErrorCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
