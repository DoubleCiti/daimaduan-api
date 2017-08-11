package com.doubleciti.daimaduan.api.repository;

import com.doubleciti.daimaduan.api.domain.Paste;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PasteRepository extends MongoRepository<Paste, String> {
    Paste findOneByHashId(String hashId);
}
