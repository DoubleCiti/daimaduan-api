package com.doubleciti.daimaduan.api.domain;

public class Code {
    private String title;

    private String Syntax;

    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSyntax() {
        return Syntax;
    }

    public void setSyntax(String syntax) {
        Syntax = syntax;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
