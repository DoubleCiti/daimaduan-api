package com.doubleciti.daimaduan.api.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class ApiExceptionMapper implements ExceptionMapper<ApiException> {
    @Override
    public Response toResponse(ApiException exception) {
        return Response.status(exception.getStatus())
                .entity(new ErrorMessage(exception))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
