package com.doubleciti.daimaduan.api.exception;

import org.springframework.http.HttpStatus;

public class ApiException extends Exception {
    private HttpStatus status;

    private int code;

    private String message;

    public ApiException(HttpStatus status, int code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
