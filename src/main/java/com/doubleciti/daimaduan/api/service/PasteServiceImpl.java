package com.doubleciti.daimaduan.api.service;

import com.doubleciti.daimaduan.api.domain.Code;
import com.doubleciti.daimaduan.api.domain.Paste;
import com.doubleciti.daimaduan.api.model.PasteModel;
import com.doubleciti.daimaduan.api.repository.PasteRepository;
import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

@Service
public class PasteServiceImpl implements PasteService {
    private final PasteRepository pasteRepository;

    private final UserService userService;

    private final MessageDigest messageDigest;

    @Autowired
    public PasteServiceImpl(PasteRepository pasteRepository,
                            UserService userService) throws NoSuchAlgorithmException {
        this.pasteRepository = pasteRepository;
        this.userService = userService;

        messageDigest = MessageDigest.getInstance("MD5");
    }

    @Override
    public Optional<Paste> save(PasteModel model) {
        Paste paste = new Paste();
        paste.setTitle(model.getTitle());
        paste.setUser(userService.getCurrentUser());
        paste.setHashId(generateNextHashId());

        model.getCodes().forEach(codeModel -> {
            Code code = new Code();
            code.setTitle(codeModel.getTitle());
            code.setContent(codeModel.getContent());

            paste.addCode(code);
        });

        while (true) {
            try {
                pasteRepository.save(paste);

                break;
            } catch (DuplicateKeyException e) {
                paste.setHashId(generateNextHashId());
            }
        }

        return Optional.of(paste);
    }

    @Override
    public List<Paste> findAll() {
        return pasteRepository.findAll();
    }

    @Override
    public Optional<Paste> findOneByHashId(String hashId) {
        return Optional.ofNullable(pasteRepository.findOneByHashId(hashId));
    }

    private String generateNextHashId() {
        return Hashing.sha256()
                .newHasher()
                .putLong(pasteRepository.count())
                .hash()
                .toString().substring(0, getDigestNumber());
    }

    private Integer getDigestNumber() {
        return Math.toIntExact(pasteRepository.count() / 10000) + 5;
    }
}
