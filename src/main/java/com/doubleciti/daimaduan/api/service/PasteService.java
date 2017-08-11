package com.doubleciti.daimaduan.api.service;

import com.doubleciti.daimaduan.api.domain.Paste;
import com.doubleciti.daimaduan.api.model.PasteModel;

import java.util.List;
import java.util.Optional;

public interface PasteService {
    Optional<Paste> save(PasteModel model);

    List<Paste> findAll();

    Optional<Paste> findOneByHashId(String hashId);
}
