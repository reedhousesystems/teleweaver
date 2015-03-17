package com.reedhousesystems.services.core.profile.resource;

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

import org.springframework.stereotype.Component;

import com.reedhousesystems.services.core.profile.dto.DemographicDTO;
import com.reedhousesystems.services.core.profile.exception.AuthorizationException;
import com.reedhousesystems.services.core.profile.model.AuthenticatedUserToken;
import com.reedhousesystems.services.core.profile.model.Contact;
import com.reedhousesystems.services.core.profile.model.Demographic;
import com.reedhousesystems.services.core.profile.model.ExternalUser;
import com.reedhousesystems.services.core.profile.request.ContactRequest;
import com.reedhousesystems.services.core.profile.request.CreateUserRequest;
import com.reedhousesystems.services.core.profile.request.LoginRequest;
import com.reedhousesystems.services.core.profile.request.PasswordRequest;
import com.reedhousesystems.services.core.profile.request.StatusRequest;
import com.reedhousesystems.services.core.profile.request.UserRequest;

@Path("/user")
@Component
@Produces({MediaType.APPLICATION_JSON})
public class UserResource extends BaseUserResource {

    @RolesAllowed("administrator")
    @PermitAll
    @POST
	@Consumes({MediaType.APPLICATION_JSON})
    public Response createUser(CreateUserRequest request) {
        AuthenticatedUserToken token = userService.createUser(request);
        URI location = uriInfo.getAbsolutePathBuilder().path(token.getUserId()).build();
        return Response.created(location).entity(token).build();
    }

    @RolesAllowed({"administrator", "authenticated"})
    @Path("{userId}")
    @PUT
	@Consumes({MediaType.APPLICATION_JSON})
    public Response updateUser(@Context SecurityContext sc, @PathParam("userId") String userId, UserRequest request) {
        ExternalUser userMakingRequest = (ExternalUser)sc.getUserPrincipal();
        if (userMakingRequest.getRole().equals("authenticated")) {
        	if (!userMakingRequest.getId().equals(userId)) {
        		throw new AuthorizationException("User not authorized to modify profile details");
        	}
        }
        userService.updateUser(userId, request);
        return Response.noContent().build();
    }
    
    @RolesAllowed({"administrator", "authenticated"})
    @Path("{userId}/contact")
    @PUT
	@Consumes({MediaType.APPLICATION_JSON})
    public Response updateUser(@Context SecurityContext sc, @PathParam("userId") String userId, ContactRequest request) {
    	ExternalUser userMakingRequest = (ExternalUser)sc.getUserPrincipal();
        if (userMakingRequest.getRole().equals("authenticated")) {
        	if (!userMakingRequest.getId().equals(userId)) {
        		throw new AuthorizationException("User not authorized to modify contacts");
        	}
        }
    	userService.updateUser(userId, request);
    	return Response.noContent().build();
    }
    
    @RolesAllowed({"administrator", "authenticated"})
    @Path("{userId}/demographics")
    @PUT
	@Consumes({MediaType.APPLICATION_JSON})
    public Response updateUser(@Context SecurityContext sc, @PathParam("userId") String userId, DemographicDTO demographicDTO) {
    	ExternalUser userMakingRequest = (ExternalUser)sc.getUserPrincipal();
        if (userMakingRequest.getRole().equals("authenticated")) {
        	if (!userMakingRequest.getId().equals(userId)) {
        		throw new AuthorizationException("User not authorized to modify demographics");
        	}
        }
    	userService.updateUser(userId, demographicDTO);
    	return Response.noContent().build();
    }
    
    @RolesAllowed("administrator")
    @Path("{userId}/status")
    @PUT
	@Consumes({MediaType.APPLICATION_JSON})
    public Response updateUser(@PathParam("userId") String userId, StatusRequest request) {
    	userService.updateUser(userId, request);
    	return Response.noContent().build();
    }

    @RolesAllowed("administrator")
    @Path("{userId}")
    @DELETE
    public Response deleteUser(@Context SecurityContext sc, @PathParam("userId") String userId) {
        ExternalUser userMakingRequest = (ExternalUser)sc.getUserPrincipal();
        if (userMakingRequest.getId().equals(userId)) {
        	throw new AuthorizationException("You cannot delete your own account.");
        }
        userService.deleteUser(userId);
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
    
    @RolesAllowed({"administrator", "authenticated"})
    @Path("{userId}/demographic")
    @GET
    public Response getDemographics(@Context SecurityContext sc, @PathParam("userId") String userId) {
    	ExternalUser userMakingRequest = (ExternalUser)sc.getUserPrincipal();
    	Demographic demographic = userService.getDemographics(userMakingRequest, userId);
    	if (demographic != null)
    		return Response.ok().entity(demographic).build();
    	else
    		return Response.noContent().build();
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
    @PUT
    public Response login(LoginRequest request) {
        AuthenticatedUserToken token = userService.login(request);
        return getLoginResponse(token);
    }
    
    @PermitAll
    @Path("{userId}/logout")
    @PUT
    public void logout(@PathParam("userId") String userId, StatusRequest request) {
    	userService.updateUser(userId, request);
    }

    @RolesAllowed({"administrator", "authenticated"})
    @Path("{userId}/resetpassword")
    @PUT
    public Response resetPassword(@Context SecurityContext sc, @PathParam("userId") String userId, PasswordRequest request) {
    	ExternalUser userMakingRequest = (ExternalUser)sc.getUserPrincipal();
    	userService.resetPassword(userMakingRequest, userId, request);
    	return Response.noContent().build();
    }

    private Response getLoginResponse(AuthenticatedUserToken token) {
        URI location = UriBuilder.fromPath(uriInfo.getBaseUri() + "user/" + token.getUserId()).build();
        return Response.ok().entity(token).contentLocation(location).build();
    }

}
