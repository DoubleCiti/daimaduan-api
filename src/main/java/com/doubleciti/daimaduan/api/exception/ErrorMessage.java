package com.doubleciti.daimaduan.api.exception;

import org.springframework.http.HttpStatus;

public class ErrorMessage {
    private int status;

    private int errorCode;

    private String errorMessage;

    public ErrorMessage(ApiException exception) {
        this.status = HttpStatus.OK.value();
        this.errorCode = exception.getCode();
        this.errorMessage = exception.getMessage();
    }

    public int getStatus() {
        return status;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
