package com.doubleciti.daimaduan.api.repository;

import com.doubleciti.daimaduan.api.domain.Paste;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PasteRepository extends MongoRepository<Paste, String> {
}
