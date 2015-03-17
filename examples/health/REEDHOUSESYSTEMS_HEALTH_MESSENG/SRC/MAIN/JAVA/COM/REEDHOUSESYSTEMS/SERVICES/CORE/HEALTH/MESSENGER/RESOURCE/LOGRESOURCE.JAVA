package com.reedhousesystems.services.core.health.messenger.resource;

import java.util.List;

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.reedhousesystems.services.core.health.messenger.api.LogService;
import com.reedhousesystems.services.core.health.messenger.dto.BillingLog;
import com.reedhousesystems.services.core.health.messenger.dto.LogDTO;
import com.reedhousesystems.services.core.health.messenger.dto.LogTitleDTO;
import com.reedhousesystems.services.core.health.messenger.model.Demographic;
import com.reedhousesystems.services.core.health.messenger.model.ExternalUser;
import com.reedhousesystems.services.core.health.messenger.model.MessageLog;

@Path("/log")
@Component
@Produces({MediaType.APPLICATION_JSON})
public class LogResource {
		
	@Autowired
	private LogService logService;
	
	@POST
	@Path("{id}")
	@Consumes({MediaType.APPLICATION_JSON})
	public Response saveLog(LogDTO logDTO) {
		logService.saveLog(logDTO);
		return Response.noContent().build();
	}
	
	@PUT
	@Path("{id}")
	@Consumes({MediaType.APPLICATION_JSON})
	public Response updateLog(@PathParam("id") String logId, LogDTO logDTO) {
		logService.updateLog(logId, logDTO);
		return Response.noContent().build();
	}
	
	@DELETE
	@Path("{id}")
	public Response deleteLog(@PathParam("id") String logId) {
		logService.deleteLog(logId);
		return Response.noContent().build();
	}
	
	@GET
	@Path("{id}")
	public Response getLog(@PathParam("id") String logId) {
		LogDTO logDTO = logService.getLog(logId);
		return Response.ok().entity(logDTO).build();
	}
	
	@GET
	@Path("{id}/organisation")
	public Response getLogsByOrganisationId(@PathParam("id") String organisationId) {
		List<LogDTO> logDTOs = logService.getLogsByOrganisationId(organisationId);
		return Response.ok().entity(logDTOs).build();
	}
	
	@GET
	@Path("{id}/billing")
	public BillingLog createBillingLog(@Context SecurityContext sc, String logId, MessageLog messageLog) {
		ExternalUser user = (ExternalUser)sc.getUserPrincipal();
		Demographic demographic = user.getDemographic();
		LogDTO logDTO = logService.getLog(logId);
		BillingLog billingLog = new BillingLog(logDTO, demographic, messageLog);
		return billingLog;
	}
	
	@RolesAllowed("authenticated")
	@GET
	public Response getTargetedLog(@Context SecurityContext sc) {
		ExternalUser userMakingRequest = (ExternalUser)sc.getUserPrincipal();
		LogDTO logDTO = logService.getTargetedLog(userMakingRequest);
		return Response.ok().entity(logDTO).build();
	}
	
	@RolesAllowed("authenticated")
	@GET
	@Path("title")
	public Response getLogTitles(@Context SecurityContext sc) {
		ExternalUser userMakingRequest = (ExternalUser)sc.getUserPrincipal();
		List<LogTitleDTO> logTitles = logService.getLogTitles(userMakingRequest);
		return Response.ok().entity(logTitles).build();
	}
}