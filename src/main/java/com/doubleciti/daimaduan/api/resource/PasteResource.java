package com.doubleciti.daimaduan.api.resource;

import com.doubleciti.daimaduan.api.domain.Paste;
import com.doubleciti.daimaduan.api.model.PasteModel;
import com.doubleciti.daimaduan.api.service.PasteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

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
    public List<Paste> listAllPastes() {
        return pasteService.findAll();
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response create(PasteModel model) {
        return pasteService.save(model)
                .flatMap(paste -> {
                    try {
                        return Optional.ofNullable(Response
                                .created(new URI(String.format("/resources/pastes/%s", paste.getHashId())))
                                .entity(paste)
                                .build());
                    } catch (URISyntaxException e) {
                        return Optional.empty();
                    }
                })
                .orElseGet(() -> Response.serverError().build());
    }

    @GET
    @Path("/{hashId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response view(@PathParam(value = "hashId") String hashId) {
        return pasteService.findOneByHashId(hashId)
                .map(paste -> Response.ok().entity(paste).build())
                .orElseGet(() ->Response.status(Response.Status.NOT_FOUND).build());
    }
}
