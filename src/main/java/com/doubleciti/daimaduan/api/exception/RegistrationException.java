package com.doubleciti.daimaduan.api.exception;

import javax.ws.rs.core.Response.Status;

public class RegistrationException extends ApiException {
    public RegistrationException(Status status, String message) {
        super(status, ErrorCode.DUPLICATE_USER_EMAIL.getCode(), message);
    }
}
