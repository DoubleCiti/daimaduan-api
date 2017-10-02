package com.doubleciti.daimaduan.api.controller;

import com.doubleciti.daimaduan.api.domain.Paste;
import com.doubleciti.daimaduan.api.model.PasteCreateModel;
import com.doubleciti.daimaduan.api.model.PasteModel;
import com.doubleciti.daimaduan.api.service.PasteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/resources/pastes")
@Api(value = "Pastes API", produces = MediaType.APPLICATION_JSON_VALUE)
public class PasteController {
    private final PasteService pasteService;

    @Autowired
    public PasteController(PasteService pasteService) {
        this.pasteService = pasteService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "List all pastes",
                  response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Resource found"),
            @ApiResponse(code = 404, message = "Resource not found")
    })
    public List<PasteModel> listAllPastes() {
        return pasteService.findAll()
                .stream()
                .map(Paste::toPasteModel)
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Create a paste",
                  response = PasteModel.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Resource found"),
            @ApiResponse(code = 404, message = "Resource not found")
    })
    public ResponseEntity<PasteModel> create(PasteCreateModel model) {
        return pasteService.save(model)
                .flatMap(paste -> {
                    try {
                        return Optional.of(new ResponseEntity<>(paste.toPasteModel(), HttpStatus.OK));
                    } catch (Exception e) {
                        return Optional.empty();
                    }
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/{hashId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Show a paste",
                  response = PasteModel.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Resource found"),
            @ApiResponse(code = 404, message = "Resource not found")
    })
    public ResponseEntity<PasteModel> view(@PathVariable String hashId) {
        return pasteService.findOneByHashId(hashId)
                .map(paste -> new ResponseEntity<>(paste.toPasteModel(), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
