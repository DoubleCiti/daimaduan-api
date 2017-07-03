package com.doubleciti.daimaduan.api.resource;

import com.doubleciti.daimaduan.api.domain.User;
import com.doubleciti.daimaduan.api.model.UserInfoModel;
import com.doubleciti.daimaduan.api.model.UserLoginModel;
import com.doubleciti.daimaduan.api.model.UserRegisterModel;
import com.doubleciti.daimaduan.api.service.SecurityService;
import com.doubleciti.daimaduan.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;

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

        User user = userService.authenticateUser(model);
        if (user == null) {
            return Response.serverError().build();
        }

        securityService.login(user);

        return Response.ok(user).build();
    }

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(UserRegisterModel model) throws URISyntaxException {
        if (model == null) {
            return Response.serverError().build();
        }

        UserInfoModel user = userService.save(model).toUserInfo();

        return Response.created(new URI(String.format("/users/%s", user.getId()))).entity(user).build();
    }
}
