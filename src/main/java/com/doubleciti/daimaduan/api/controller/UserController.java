package com.doubleciti.daimaduan.api.controller;

import com.doubleciti.daimaduan.api.domain.User;
import com.doubleciti.daimaduan.api.exception.RegistrationException;
import com.doubleciti.daimaduan.api.model.UserInfoModel;
import com.doubleciti.daimaduan.api.model.UserLoginModel;
import com.doubleciti.daimaduan.api.model.UserRegisterModel;
import com.doubleciti.daimaduan.api.service.SecurityService;
import com.doubleciti.daimaduan.api.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;
import java.util.Optional;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/user")
@Api(value = "Pastes API", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    private final UserService userService;

    private final SecurityService securityService;

    @Autowired
    public UserController(UserService userService, SecurityService securityService) {
        this.userService = userService;
        this.securityService = securityService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserInfoModel> login(UserLoginModel model) {
        if (model == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return userService.findUser(model)
                .map(user -> {
                    securityService.login(user);
                    return new ResponseEntity<>(user.toUserInfo(), HttpStatus.OK);})
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.UNAUTHORIZED));
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserInfoModel> register(UserRegisterModel model) throws URISyntaxException, RegistrationException {
        if (model == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Optional<User> userOptional;
        try {
            userOptional = userService.save(model);
        } catch (DuplicateKeyException e) {
            throw new RegistrationException(HttpStatus.BAD_REQUEST, "username/email duplicate");
        }

        return userOptional
                .map(user -> new ResponseEntity<>(user.toUserInfo(), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }
}
