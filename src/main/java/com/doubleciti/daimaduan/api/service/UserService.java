package com.doubleciti.daimaduan.api.service;

import com.doubleciti.daimaduan.api.domain.User;
import com.doubleciti.daimaduan.api.model.UserLoginModel;
import com.doubleciti.daimaduan.api.model.UserRegisterModel;

public interface UserService {
    User save(UserRegisterModel model);

    User authenticateUser(UserLoginModel model);
}
