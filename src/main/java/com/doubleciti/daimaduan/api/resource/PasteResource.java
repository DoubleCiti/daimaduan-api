package com.doubleciti.daimaduan.api.resource;

import com.doubleciti.daimaduan.api.domain.Paste;
import com.doubleciti.daimaduan.api.repository.PasteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/resources/pastes")
@Component
public class PasteResource {
    private final PasteRepository pasteRepository;

    @Autowired
    public PasteResource(PasteRepository pasteRepository) {
        this.pasteRepository = pasteRepository;
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Paste> listAllPastes() {
        return pasteRepository.findAll();
    }
}
