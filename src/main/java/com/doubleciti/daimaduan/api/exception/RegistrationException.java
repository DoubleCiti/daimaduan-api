package com.doubleciti.daimaduan.api.exception;

import org.springframework.http.HttpStatus;

public class RegistrationException extends ApiException {
    public RegistrationException(HttpStatus status, String message) {
        super(status, ErrorCode.DUPLICATE_USER_EMAIL.getCode(), message);
    }
}
