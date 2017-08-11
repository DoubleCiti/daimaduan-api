package com.doubleciti.daimaduan.api.domain;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Syntax {
    private String key;

    private String name;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
