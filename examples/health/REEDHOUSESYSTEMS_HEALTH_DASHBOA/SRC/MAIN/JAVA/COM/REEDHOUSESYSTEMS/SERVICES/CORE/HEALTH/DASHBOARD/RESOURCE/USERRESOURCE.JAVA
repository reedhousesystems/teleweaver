package com.reedhousesystems.services.core.health.dashboard.resource;

import java.net.URI;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.reedhousesystems.services.core.health.dashboard.api.UserService;
import com.reedhousesystems.services.core.health.dashboard.api.VerificationTokenService;
import com.reedhousesystems.services.core.health.dashboard.config.ApplicationConfig;
import com.reedhousesystems.services.core.health.dashboard.exception.AuthorizationException;
import com.reedhousesystems.services.core.health.dashboard.model.AuthenticatedUserToken;
import com.reedhousesystems.services.core.health.dashboard.model.Contact;
import com.reedhousesystems.services.core.health.dashboard.model.ExternalUser;
import com.reedhousesystems.services.core.health.dashboard.requests.CreateUserRequest;
import com.reedhousesystems.services.core.health.dashboard.requests.LoginRequest;
import com.reedhousesystems.services.core.health.dashboard.requests.PasswordRequest;
import com.reedhousesystems.services.core.health.dashboard.requests.UpdateContactsRequest;
import com.reedhousesystems.services.core.health.dashboard.requests.UpdateStatusRequest;
import com.reedhousesystems.services.core.health.dashboard.requests.UpdateUserRequest;

@Path("/user")
@Component
@Produces({MediaType.APPLICATION_JSON})
public class UserResource {
	
	@Autowired
    protected UserService userService;

    @Autowired
    protected VerificationTokenService verificationTokenService;

    @Autowired
    protected ApplicationConfig config;

    @Context
    protected UriInfo uriInfo;

    @RolesAllowed("administrator")
    @POST
	@Consumes({MediaType.APPLICATION_JSON})
    public Response createUser(CreateUserRequest request) {
        userService.createUser(request);
        return Response.noContent().build();
    }

    @RolesAllowed({"administrator", "authenticated"})
    @Path("{userId}")
    @PUT
	@Consumes({MediaType.APPLICATION_JSON})
    public Response updateUser(@Context SecurityContext sc, @PathParam("userId") String userId, UpdateUserRequest request) {
        ExternalUser userMakingRequest = (ExternalUser)sc.getUserPrincipal();
        if (userMakingRequest.getRole().equals("authenticated")) {
        	if (!userMakingRequest.getId().equals(userId)) {
        		throw new AuthorizationException("User not authorized to modify this profile");
        	}
        }
        userService.updateUser(userId, request);
        return Response.noContent().build();
    }
    
    @RolesAllowed({"administrator", "authenticated"})
    @Path("{userId}/contact")
    @PUT
	@Consumes({MediaType.APPLICATION_JSON})
    public Response updateUser(@Context SecurityContext sc, @PathParam("userId") String userId, UpdateContactsRequest request) {
    	ExternalUser userMakingRequest = (ExternalUser)sc.getUserPrincipal();
    	if (!userMakingRequest.getId().equals(userId)) {
    		throw new AuthorizationException("User not authorised to update contacts");
    	}
    	userService.updateUser(userId, request);
    	return Response.noContent().build();
    }
    
    @RolesAllowed("administrator")
    @Path("{userId}/status")
    @PUT
	@Consumes({MediaType.APPLICATION_JSON})
    public Response updateUser(@PathParam("userId") String userId, UpdateStatusRequest request) {
    	userService.updateUser(userId, request);
    	return Response.noContent().build();
    }

    @RolesAllowed("administrator")
    @Path("{userId}")
    @DELETE
    public Response deleteUser(@Context SecurityContext sc, @PathParam("userId") String userId) {
        ExternalUser userMakingRequest = (ExternalUser)sc.getUserPrincipal();
        userService.deleteUser(userMakingRequest, userId);
        return Response.noContent().build();
    }

    @RolesAllowed({"administrator", "authenticated"})
    @Path("{userId}")
    @GET
    public Response getUser(@Context SecurityContext sc, @PathParam("userId") String userId) {
        ExternalUser userMakingRequest = (ExternalUser)sc.getUserPrincipal();
        ExternalUser user =  userService.getUser(userMakingRequest, userId);
        return Response.ok().entity(user).build();
    }
    
    @RolesAllowed({"administrator", "authenticated"})
    @Path("{userId}/contact")
    @GET
    public Response getContacts(@Context SecurityContext sc, @PathParam("userId") String userId) {
    	ExternalUser userMakingRequest = (ExternalUser)sc.getUserPrincipal();
    	Contact contact = userService.getContacts(userMakingRequest, userId);
    	return Response.ok().entity(contact).build();
    }
    
    @RolesAllowed("administrator")
    @GET
    public Response getAllUsers(@Context SecurityContext sc) {
    	ExternalUser userMakingRequest = (ExternalUser)sc.getUserPrincipal();
    	List<ExternalUser> userList = userService.getAllUsers(userMakingRequest);
    	return Response.ok().entity(userList).build();
    }

    @PermitAll
    @Path("login")
    @POST
    public Response login(LoginRequest request) {
        AuthenticatedUserToken token = userService.login(request);
        return getLoginResponse(token);
    }
    
    @PermitAll
    @Path("{userId}/logout")
    @PUT
    public void logout(@PathParam("userId") String userId, UpdateStatusRequest request) {
    	userService.updateUser(userId, request);
    }

    /*@RolesAllowed({"administrator"})
    @Path("{userId}/passwordreset")
    @POST
    public void resetPassword(@PathParam("userId") String userId, PasswordRequest request) {
    	userService.resetPassword(userId, request);
    }*/

    @RolesAllowed({"administrator", "authenticated"})
    @Path("{userId}/resetpassword")
    @PUT
    public void resetPassword(@Context SecurityContext sc, @PathParam("userId") String userId, PasswordRequest request) {
    	ExternalUser userMakingRequest = (ExternalUser)sc.getUserPrincipal();
    	userService.resetPassword(userMakingRequest, userId, request);
    }

    private Response getLoginResponse(AuthenticatedUserToken token) {
        URI location = UriBuilder.fromPath(uriInfo.getBaseUri() + "user/" + token.getUserId()).build();
        return Response.ok().entity(token).contentLocation(location).build();
    }

}
