package com.doubleciti.daimaduan.api.service;

import com.doubleciti.daimaduan.api.domain.User;
import com.doubleciti.daimaduan.api.exception.RegistrationException;
import com.doubleciti.daimaduan.api.model.UserLoginModel;
import com.doubleciti.daimaduan.api.model.UserRegisterModel;
import com.doubleciti.daimaduan.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> save(UserRegisterModel model) throws RegistrationException {
        User user = new User(model.getUsername(), model.getEmail(), new BCryptPasswordEncoder().encode(model.getPassword()));

        userRepository.save(user);

        return Optional.of(user);
    }

    public Optional<User> findUser(UserLoginModel model) {
        return Optional.of(userRepository.findOneByEmail(model.getEmail()));
    }
}
