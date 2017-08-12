package com.doubleciti.daimaduan.api.domain;

import com.doubleciti.daimaduan.api.model.CodeModel;
import com.doubleciti.daimaduan.api.model.PasteModel;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Document
public class Paste {
    @Id
    private ObjectId id;

    @DBRef
    private User user;

    private String title;

    private String hashId;

    private List<Code> codes;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date createdAt;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date updatedAt;

    public Paste() {
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public List<Code> getCodes() {
        return codes;
    }

    public void setCodes(List<Code> codes) {
        this.codes = codes;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void addCode(Code code) {
        if (codes == null || codes.size() ==0) {
            codes = new ArrayList<>();
        }

        codes.add(code);
    }

    private List<CodeModel> getCodeModels() {
        return codes.stream()
                .map(code -> new CodeModel(code.getTitle(), code.getContent()))
                .collect(Collectors.toList());
    }

    public PasteModel toPasteModel() {
        return new PasteModel(getHashId(),
                getUser().toUserInfo(),
                getTitle(),
                getCodeModels());
    }
}
