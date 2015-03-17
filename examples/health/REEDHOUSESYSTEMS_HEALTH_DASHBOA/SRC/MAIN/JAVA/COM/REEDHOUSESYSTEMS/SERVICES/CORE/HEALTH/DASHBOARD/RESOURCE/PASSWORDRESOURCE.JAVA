package com.reedhousesystems.services.core.health.dashboard.resource;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.reedhousesystems.services.core.health.dashboard.api.VerificationTokenService;
import com.reedhousesystems.services.core.health.dashboard.requests.LostPasswordRequest;
import com.reedhousesystems.services.core.health.dashboard.requests.PasswordRequest;

/**
 *
 * @author: C. Gremu
 */
@Path("password")
@Component
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class PasswordResource {

    @Autowired
    protected VerificationTokenService verificationTokenService;

    @PermitAll
    @Path("tokens")
    @POST
    public Response sendEmailToken(LostPasswordRequest request) {
        verificationTokenService.sendLostPasswordToken(request);
        return Response.ok().build();
    }

    @RolesAllowed({"administrator", "authenticated"})
    @Path("user/{userId}")
    @POST
    public Response resetPassword(@PathParam("userId") String userId, PasswordRequest request) {
        verificationTokenService.resetPassword(userId, request);
        return Response.ok().build();
    }
}
