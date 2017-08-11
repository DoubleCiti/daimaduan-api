package com.doubleciti.daimaduan.api.model;

import java.util.List;

public class PasteModel {
    private String title;

    private List<CodeModel> codes;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<CodeModel> getCodes() {
        return codes;
    }

    public void setCodes(List<CodeModel> codes) {
        this.codes = codes;
    }
}
