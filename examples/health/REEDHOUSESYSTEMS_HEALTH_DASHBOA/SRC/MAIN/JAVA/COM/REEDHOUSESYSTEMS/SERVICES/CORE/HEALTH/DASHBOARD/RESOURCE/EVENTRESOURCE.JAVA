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

import com.reedhousesystems.services.core.health.dashboard.api.EventService;
import com.reedhousesystems.services.core.health.dashboard.dto.EventDTO;
import com.reedhousesystems.services.core.health.dashboard.dto.EventDTOLite;
import com.reedhousesystems.services.core.health.dashboard.dto.EventRegistrationDTO;
import com.reedhousesystems.services.core.health.dashboard.dto.TargetDTO;
import com.reedhousesystems.services.core.health.dashboard.dto.UUIDDTO;
import com.reedhousesystems.services.core.health.dashboard.filter.Cors;
import com.reedhousesystems.services.core.health.dashboard.filter.CorsPreflight;
import com.reedhousesystems.services.core.health.dashboard.model.ExternalUser;
import com.reedhousesystems.services.core.health.dashboard.requests.EventRequest;

@Path("/event")
@Component
@Produces({MediaType.APPLICATION_JSON})
public class EventResource {
	
	@Autowired
	EventService eventService;

    @Context
    protected UriInfo uriInfo;
	
	@OPTIONS
	@CorsPreflight
	public Response optionRequest() {
		return Response.ok().build();
	}
	
	@Path("{id}")
	@POST
	@Cors
	@Consumes({MediaType.APPLICATION_JSON})
	public Response registerForAnEvent(@PathParam("id") String eventId, EventRegistrationDTO eventRegistrationDTO) {
		return Response.noContent().build();
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	public Response saveEvent(EventRequest request) throws Exception {
		UUIDDTO uuidDTO = null;
		try {
			uuidDTO = eventService.saveEvent(request);
		} catch (Exception ex) {
			Response.serverError().build();
		}
        return Response.ok().entity(uuidDTO).build();
	}

	@PUT
	@Path("{id}")
	@Consumes({MediaType.APPLICATION_JSON})
	public Response updateEvent(@PathParam("id") String eventId, EventRequest request) throws Exception {
		try {
			eventService.updateEvent(eventId, request);
		} catch (Exception ex) {
			Response.serverError().build();
		}
		return Response.noContent().build();
	}

	@PUT
	@Path("{id}/status/{status}")
	@Consumes({MediaType.APPLICATION_JSON})
	public void updateEvent(@PathParam("id") String eventId, @PathParam("status") String status) throws Exception {
		try {
			eventService.updateEvent(eventId, status);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	@PUT
	@Path("{id}/target")
	@Consumes({MediaType.APPLICATION_JSON})
	public Response setEventTarget(@PathParam("id") String eventId, TargetDTO target) throws Exception {
		try {
			eventService.setEventTarget(eventId, target);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return Response.noContent().build();
	}
	
	@GET
	@Path("{id}/target")
	public Response getEventTarget(@PathParam("id") String eventId) throws Exception {
		TargetDTO target = null;
		try {
			target = eventService.getEventTarget(eventId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (target == null)
			return Response.noContent().build();
		else
			return Response.ok().entity(target).build();
	}
	
	@DELETE
	@Path("{id}")
	@Produces("application/json")
	public Response deleteEvent(@PathParam("id") String eventId) throws Exception {
		try {
			eventService.deleteEvent(eventId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return Response.noContent().build();
	}
	
	@GET
	@Path("{id}")
	public Response getEventById(@PathParam("id") String eventId) throws Exception {
		EventDTOLite event = null;
		try {
			event = eventService.getEventById(eventId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return Response.ok().entity(event).build();
	}
	
	@GET
	@Path("{id}/publish")
	public Response getEventToPublish(@PathParam("id") String eventId) throws Exception {
		EventDTO event = null;
		try {
			event = eventService.getEventToPublish(eventId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return Response.ok().entity(event).build();
	}

	@RolesAllowed({"administrator", "authenticated"})
	@GET
	public Response getAllEvents(@Context SecurityContext sc) throws Exception {
        ExternalUser userMakingRequest = (ExternalUser)sc.getUserPrincipal();
		List<EventDTOLite> eventList = new ArrayList<EventDTOLite>();
		try {
			eventList = eventService.getAllEvents(userMakingRequest);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return Response.ok().entity(eventList).build();
	}
	
}