package com.doubleciti.daimaduan.api.domain;

import java.util.Collection;

public class Paste {
    private String title;

    private String hashId;

    private Collection<Code> codes;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHashId() {
        return hashId;
    }

    public void setHashId(String hashId) {
        this.hashId = hashId;
    }

    public Collection<Code> getCodes() {
        return codes;
    }

    public void setCodes(Collection<Code> codes) {
        this.codes = codes;
    }
}