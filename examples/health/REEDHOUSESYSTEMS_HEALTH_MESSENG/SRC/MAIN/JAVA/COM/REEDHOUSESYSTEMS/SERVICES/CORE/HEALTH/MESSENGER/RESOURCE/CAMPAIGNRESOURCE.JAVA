package com.reedhousesystems.services.core.health.messenger.resource;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.reedhousesystems.services.core.health.messenger.api.CampaignService;
import com.reedhousesystems.services.core.health.messenger.dto.CampaignDTO;
import com.reedhousesystems.services.core.health.messenger.filter.Cors;
import com.reedhousesystems.services.core.health.messenger.filter.CorsPreflight;
import com.reedhousesystems.services.core.health.messenger.model.ExternalUser;
import com.reedhousesystems.services.core.health.messenger.request.CampaignRequest;
import com.reedhousesystems.services.core.health.messenger.request.MessageRequest;

@Path("/campaigns")
@Component
@Produces({MediaType.APPLICATION_JSON})
public class CampaignResource {

	@Autowired
	private CampaignService campaignService;
	
	@OPTIONS
	@CorsPreflight
	public Response optionRequest() {
		return Response.ok().build();
	}

	@POST
	@Cors
	@Consumes({MediaType.APPLICATION_JSON})
	public Response saveOrUpdateCampaign(CampaignDTO campaign) {
		Integer numberOfMatchingUsers = campaignService.saveOrUpdateCampaign(campaign);
		return Response.ok().entity(numberOfMatchingUsers).build();
	}
	
	@RolesAllowed({"administrator", "authenticated"})
	@GET
	public Response getRunningCampaigns(@Context SecurityContext sc) {
        ExternalUser userMakingRequest = (ExternalUser)sc.getUserPrincipal();
		List<CampaignRequest> campaignList = campaignService.getRunningCampaigns(userMakingRequest);
		return Response.ok().entity(campaignList).build();
	}

	@RolesAllowed({"administrator", "authenticated"})
	@GET
	@Path("{id}/message")
	public Response getMessagesByCampaignId(@PathParam("id") String campaignId, @Context SecurityContext sc) {
        ExternalUser userMakingRequest = (ExternalUser)sc.getUserPrincipal();
		List<MessageRequest> messageList = campaignService.getMessagesByCampaignId(campaignId, userMakingRequest);
		return Response.ok().entity(messageList).build();
	}
}