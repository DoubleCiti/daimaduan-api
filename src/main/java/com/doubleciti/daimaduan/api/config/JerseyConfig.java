package com.doubleciti.daimaduan.api.config;

import com.doubleciti.daimaduan.api.resource.PasteResource;
import com.doubleciti.daimaduan.api.resource.UserResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        register(UserResource.class);
        register(PasteResource.class);
    }
}
