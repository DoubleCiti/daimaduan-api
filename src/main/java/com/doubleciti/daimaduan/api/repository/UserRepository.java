package com.doubleciti.daimaduan.api.repository;

import com.doubleciti.daimaduan.api.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    User findOneByEmail(String email);

    User findOneByUsername(String username);

    User findFirstByOrderByCreatedAtDesc();
}
