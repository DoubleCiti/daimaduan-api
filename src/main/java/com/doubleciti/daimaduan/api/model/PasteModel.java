package com.doubleciti.daimaduan.api.model;

import java.util.List;

public class PasteModel {
    private String id;

    private UserInfoModel user;

    private String title;

    private List<CodeModel> codes;

    public PasteModel() {}

    public PasteModel(String hashId, UserInfoModel userInfoModel, String title, List<CodeModel> codeModels) {
        this.id = hashId;
        this.user = userInfoModel;
        this.title = title;
        this.codes = codeModels;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserInfoModel getUser() {
        return user;
    }

    public void setUser(UserInfoModel user) {
        this.user = user;
    }

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
