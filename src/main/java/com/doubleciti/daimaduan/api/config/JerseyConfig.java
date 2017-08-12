package com.doubleciti.daimaduan.api.config;

import com.doubleciti.daimaduan.api.exception.ApiExceptionMapper;
import com.doubleciti.daimaduan.api.resource.PasteResource;
import com.doubleciti.daimaduan.api.resource.UserResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        register(ApiExceptionMapper.class);

        register(UserResource.class);
        register(PasteResource.class);
    }
}
