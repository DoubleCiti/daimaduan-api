package com.doubleciti.daimaduan.api.service;

import com.doubleciti.daimaduan.api.domain.User;
import com.doubleciti.daimaduan.api.exception.RegistrationException;
import com.doubleciti.daimaduan.api.model.UserLoginModel;
import com.doubleciti.daimaduan.api.model.UserRegisterModel;
import com.doubleciti.daimaduan.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> save(UserRegisterModel model) throws RegistrationException {
        User user = new User(model.getUsername(),
                model.getEmail(),
                new BCryptPasswordEncoder().encode(model.getPassword()));
        user.setSeqId(getNextSequence());

        while (true) {
            try {
                userRepository.save(user);

                break;
            } catch (DuplicateKeyException e) {
                // well, the way of finding out which key is duplicated is silly
                if (e.getMessage().indexOf("seqId") > 0 && e.getMessage().indexOf(user.getSeqId()) > 0) {
                    user.setSeqId(getNextSequence());
                } else {
                    throw e;
                }
            }
        }

        return Optional.of(user);
    }

    private Integer getNextSequence() {
        User user = userRepository.findFirstByOrderByCreatedAtDesc();
        if (user == null) {
            return 1000; // starts with 1000
        }
        return user.getSeqId() + 1;
    }

    public Optional<User> findUser(UserLoginModel model) {
        return Optional.of(userRepository.findOneByEmail(model.getEmail()));
    }

    @Override
    public User getCurrentUser() {
        return userRepository.findOneByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
