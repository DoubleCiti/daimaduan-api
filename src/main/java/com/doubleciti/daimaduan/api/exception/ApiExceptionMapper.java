package com.doubleciti.daimaduan.api.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class ApiExceptionMapper implements ExceptionMapper<ApiException> {
    private static Logger logger = LoggerFactory.getLogger("daimaduan-api");

    @Override
    public Response toResponse(ApiException exception) {
        logger.error("status code: {}, message: {}", exception.getStatus(), exception.getMessage());
        return Response.status(exception.getStatus())
                .entity(new ErrorMessage(exception))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
