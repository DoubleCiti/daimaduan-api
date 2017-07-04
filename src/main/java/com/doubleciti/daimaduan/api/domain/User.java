package com.doubleciti.daimaduan.api.domain;

import com.doubleciti.daimaduan.api.model.UserInfoModel;
import org.hibernate.validator.constraints.Email;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

public class User {
    @Id
    private String id;

    @Indexed(unique=true)
    private String username;

    @Email
    @Indexed(unique=true)
    private String email;

    private String password;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserInfoModel toUserInfo() {
        return new UserInfoModel(id, username, email);
    }
}
