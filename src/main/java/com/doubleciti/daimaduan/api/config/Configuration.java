package com.doubleciti.daimaduan.api.config;

import com.doubleciti.daimaduan.api.resource.PasteResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public class Configuration extends ResourceConfig {
    public Configuration() {
        register(PasteResource.class);
    }
}
