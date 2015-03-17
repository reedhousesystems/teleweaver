package com.reedhousesystems.services.core.health.dashboard.resource;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.reedhousesystems.services.core.health.dashboard.api.CampaignService;
import com.reedhousesystems.services.core.health.dashboard.dto.CampaignDTO;
import com.reedhousesystems.services.core.health.dashboard.dto.TargetDTO;
import com.reedhousesystems.services.core.health.dashboard.dto.UUIDDTO;
import com.reedhousesystems.services.core.health.dashboard.filter.Cors;
import com.reedhousesystems.services.core.health.dashboard.filter.CorsPreflight;
import com.reedhousesystems.services.core.health.dashboard.model.Campaign;
import com.reedhousesystems.services.core.health.dashboard.model.ExternalUser;
import com.reedhousesystems.services.core.health.dashboard.model.Message;
import com.reedhousesystems.services.core.health.dashboard.model.Target;
import com.reedhousesystems.services.core.health.dashboard.requests.CampaignRequest;
import com.reedhousesystems.services.core.health.dashboard.requests.MessageRequest;

@Path("/campaign")
@Component
@Produces({MediaType.APPLICATION_JSON})
public class CampaignResource {
	
	@Autowired
	private CampaignService campaignService;

    @Context
    protected UriInfo uriInfo;
	
	@OPTIONS
	@CorsPreflight
	public Response optionRequest() {
		return Response.ok().build();
	}

	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	public Response saveCampaign(CampaignRequest request) throws Exception {
		UUIDDTO uuidDTO = null;
		try {
			uuidDTO = campaignService.saveCampaign(request);
		} catch (Exception ex) {
			return Response.serverError().build();
		}
		return Response.ok().entity(uuidDTO).build();
	}

	@PUT
	@Path("{id}")
	@Consumes({MediaType.APPLICATION_JSON})
	public Response updateCampaign(@PathParam("id") String campaignId, CampaignRequest request) throws Exception {
		try {
			campaignService.updateCampaign(campaignId, request);
		} catch (Exception ex) {
			return Response.serverError().build();
		}
		return Response.noContent().build();
	}

	@PUT
	@Path("{id}/status/{status}")
	@Consumes({MediaType.APPLICATION_JSON})
	public Response updateCampaign(@PathParam("id") String campaignId, @PathParam("status") String status) throws Exception {
		try {
			campaignService.updateCampaign(campaignId, status);
		} catch (Exception ex) {
			return Response.serverError().build();
		}
		return Response.noContent().build();
	}

	@DELETE
	@Path("{id}")
	public Response deleteCampaign(@PathParam("id") String campaignId) throws Exception {
		try {
			campaignService.deleteCampaign(campaignId);
		} catch (Exception ex) {
			return Response.serverError().build();
		}
		return Response.noContent().build();
	}

	@GET
	@Path("{id}")
	public Response getCampaignById(@PathParam("id") String campaignId) throws Exception {
		CampaignRequest campaign = null;
		try {
			campaign = campaignService.getCampaignById(campaignId);
		} catch (Exception ex) {
			return Response.serverError().build();
		}
		return Response.ok().entity(campaign).build();
	}

	@GET
	@Path("{id}/publish")
	public Response getCampaignToPublish(@PathParam("id") String campaignId) throws Exception {
		CampaignDTO campaign = null;
		try {
			campaign = campaignService.getCampaignToPublish(campaignId);
		} catch (Exception ex) {
			return Response.serverError().build();
		}
		return Response.ok().entity(campaign).build();
	}
	
	@RolesAllowed({"administrator", "authenticated"})
	@GET
	public Response getAllCampaigns(@Context SecurityContext sc) throws Exception {
        ExternalUser userMakingRequest = (ExternalUser)sc.getUserPrincipal();
		List<Campaign> campaignList = new ArrayList<Campaign>();
		try {
			campaignList = campaignService.getAllCampaigns(userMakingRequest);
		} catch (Exception ex) {
			return Response.serverError().build();
		}
		return Response.ok().entity(campaignList).build();
	}
	
	@PUT
	@Path("{id}/target")
	@Consumes({MediaType.APPLICATION_JSON})
	public Response setCampaignTarget(@PathParam("id") String campaignId, TargetDTO target) throws Exception {
		try {
			campaignService.setCampaignTarget(campaignId, target);
		} catch (Exception ex) {
			return Response.serverError().build();
		}
		return Response.noContent().build();
	}

	@GET
	@Path("{id}/target")
	public Response getCampaignTarget(@PathParam("id") String campaignId) throws Exception {
		Target target = null;
		try {
			target = campaignService.getCampaignTarget(campaignId);
		} catch (Exception ex) {
			return Response.serverError().build();
		}
		if (target == null)
			return Response.noContent().build();
		else
			return Response.ok().entity(target).build();
	}

	@PUT
	@Path("{id}/message")
	@Consumes({MediaType.APPLICATION_JSON})
	public Response saveMessage(@PathParam("id") String campaignId, MessageRequest request) throws Exception {
		try {
			campaignService.saveMessage(campaignId, request);
		} catch (Exception ex) {
			return Response.serverError().build();
		}
		return Response.noContent().build();
	}

	@PUT
	@Path("message/{id}")
	@Consumes({MediaType.APPLICATION_JSON})
	public Response updateMessage(@PathParam("id") String messageId, MessageRequest request) throws Exception {
		try {
			campaignService.updateMessage(messageId, request);
		} catch (Exception ex) {
			return Response.serverError().build();
		}
		return Response.noContent().build();
	}

	@DELETE
	@Path("message/{id}")
	public Response deleteMessage(@PathParam("id") String messageId) throws Exception {
		try {
			campaignService.deleteMessage(messageId);
		} catch (Exception ex) {
			return Response.serverError().build();
		}
		return Response.noContent().build();
	}

	@GET
	@Path("message/{id}")
	public Response getMessageById(@PathParam("id") String messageId) throws Exception {
		Message message = null;
		try {
			message = campaignService.getMessageById(messageId);
		} catch (Exception ex) {
			return Response.serverError().build();
		}
		return Response.ok().entity(message).build();
	}

	@GET
	@Path("{id}/message")
	public Response getMessagesByCampaignId(@PathParam("id") String campaignId) throws Exception {
		List<Message> messageList = new ArrayList<Message>();
		try {
			messageList = campaignService.getMessagesByCampaignId(campaignId);
		} catch (Exception ex) {
			return Response.serverError().build();
		}
		return Response.ok().entity(messageList).build();
	}
	
	@PUT
	@Cors
	@Path("{id}/view")
	public Response updateCampaignViews(@PathParam("id") String campaignId, UUIDDTO uuidDTO) throws Exception {
		CampaignDTO campaign = null;
		try {
			campaign = campaignService.updateCampaignViews(campaignId);
		} catch (Exception ex) {
			return Response.serverError().build();
		}
		return Response.ok().entity(campaign).build();
	}
	
	@RolesAllowed({"administrator", "authenticated"})
	@GET
	@Path("/report")
	public Response getCampaignsToReport(@Context SecurityContext sc) throws Exception {
        ExternalUser userMakingRequest = (ExternalUser)sc.getUserPrincipal();
		List<Campaign> campaignList = new ArrayList<Campaign>();
		try {
			campaignList = campaignService.getCampaignsToReport(userMakingRequest);
		} catch (Exception ex) {
			return Response.serverError().build();
		}
		return Response.ok().entity(campaignList).build();
	}
}