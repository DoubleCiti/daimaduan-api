package com.doubleciti.daimaduan.api.exception;

import javax.ws.rs.core.Response.Status;

public class ApiException extends Exception {
    private Status status;

    private int code;

    private String message;

    public ApiException(Status status, int code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public Status getStatus() {
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
