package com.doubleciti.daimaduan.api.resource;

import com.doubleciti.daimaduan.api.domain.Paste;
import com.doubleciti.daimaduan.api.model.PasteCreateModel;
import com.doubleciti.daimaduan.api.model.PasteModel;
import com.doubleciti.daimaduan.api.service.PasteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("/resources/pastes")
@Component
public class PasteResource {
    private final PasteService pasteService;

    @Autowired
    public PasteResource(PasteService pasteService) {
        this.pasteService = pasteService;
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<PasteModel> listAllPastes() {
        return pasteService.findAll()
                .stream()
                .map(Paste::toPasteModel)
                .collect(Collectors.toList());
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response create(PasteCreateModel model) {
        return pasteService.save(model)
                .flatMap(paste -> {
                    try {
                        return Optional.ofNullable(Response
                                .created(new URI(String.format("/resources/pastes/%s", paste.getHashId())))
                                .entity(paste.toPasteModel())
                                .build());
                    } catch (Exception e) {
                        return Optional.empty();
                    }
                })
                .orElseGet(() -> Response.serverError().build());
    }

    @GET
    @Path("/{hashId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response view(@PathVariable String hashId) {
        return pasteService.findOneByHashId(hashId)
                .map(paste -> Response.ok().entity(paste.toPasteModel()).build())
                .orElseGet(() ->Response.status(Response.Status.NOT_FOUND).build());
    }
}
