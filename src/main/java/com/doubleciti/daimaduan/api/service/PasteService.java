package com.doubleciti.daimaduan.api.service;

import com.doubleciti.daimaduan.api.domain.Paste;
import com.doubleciti.daimaduan.api.model.PasteCreateModel;

import java.util.List;
import java.util.Optional;

public interface PasteService {
    Optional<Paste> save(PasteCreateModel model);

    List<Paste> findAll();

    Optional<Paste> findOneByHashId(String hashId);
}
