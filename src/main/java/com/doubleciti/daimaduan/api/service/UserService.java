package com.doubleciti.daimaduan.api.service;

import com.doubleciti.daimaduan.api.domain.User;
import com.doubleciti.daimaduan.api.exception.RegistrationException;
import com.doubleciti.daimaduan.api.model.UserLoginModel;
import com.doubleciti.daimaduan.api.model.UserRegisterModel;

import java.util.Optional;

public interface UserService {
    Optional<User> save(UserRegisterModel model) throws RegistrationException;

    Optional<User> findUser(UserLoginModel model);
}
