package com.doubleciti.daimaduan.api.resource;

import com.doubleciti.daimaduan.api.domain.User;
import com.doubleciti.daimaduan.api.exception.RegistrationException;
import com.doubleciti.daimaduan.api.model.UserLoginModel;
import com.doubleciti.daimaduan.api.model.UserRegisterModel;
import com.doubleciti.daimaduan.api.service.SecurityService;
import com.doubleciti.daimaduan.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@Path("/user")
@Component
public class UserResource {
    private final UserService userService;

    private final SecurityService securityService;

    @Autowired
    public UserResource(UserService userService, SecurityService securityService) {
        this.userService = userService;
        this.securityService = securityService;
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(UserLoginModel model) {
        if (model == null) {
            return Response.serverError().build();
        }

        Optional<User> userOptional = userService.findUser(model);
        if (userOptional.isPresent()) {
            securityService.login(userOptional.get());
            return Response.ok(userOptional.get().toUserInfo()).build();
        }

        return Response.serverError().build();
    }

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(UserRegisterModel model) throws URISyntaxException, RegistrationException {
        if (model == null) {
            return Response.serverError().build();
        }

        Optional<User> userOptional;
        try {
            userOptional = userService.save(model);
        } catch (DuplicateKeyException e) {
            throw new RegistrationException(Status.BAD_REQUEST, "username/email duplicate");
        }

        if (userOptional.isPresent()) {
            return Response
                    .created(new URI(String.format("/users/%s", userOptional.get().getId())))
                    .entity(userOptional.get().toUserInfo())
                    .build();
        }

        return Response.status(Status.NOT_FOUND).build();
    }
}
