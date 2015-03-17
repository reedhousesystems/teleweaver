package com.reedhousesystems.services.core.health.messenger.resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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

import com.reedhousesystems.services.core.health.messenger.api.EventService;
import com.reedhousesystems.services.core.health.messenger.dto.EventDTO;
import com.reedhousesystems.services.core.health.messenger.dto.EventDTOLite;
import com.reedhousesystems.services.core.health.messenger.filter.CorsPreflight;
import com.reedhousesystems.services.core.health.messenger.model.ExternalUser;

@Path("/events")
@Component
@Produces({MediaType.APPLICATION_JSON})
public class EventResource {
	
	@Autowired
	private EventService eventService;
	
	@OPTIONS
	@CorsPreflight
	public Response optionRequest() {
		return Response.ok().build();
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	public Response saveEvent(EventDTO eventDTO) {
		eventService.saveEvent(eventDTO);
		return Response.noContent().build();
	}
	
	@GET
	public Response getAllEvents(@Context SecurityContext sc) {
		ExternalUser userMakingRequest = (ExternalUser)sc.getUserPrincipal();
		List<EventDTOLite> pendingEvents = eventService.getAllEvents(userMakingRequest);
		return Response.ok().entity(pendingEvents).build();
	}
	
	@POST
	@Consumes
	@Path("{id}")
	public Response registerForAnEvent(@Context SecurityContext sc, @PathParam("id") String eventId) {
		ExternalUser userMakingRequest = (ExternalUser)sc.getUserPrincipal();
		eventService.registerForAnEvent(userMakingRequest, eventId);
		return Response.noContent().build();
	}
	
	@DELETE
	@Path("{id}")
	public Response unregisterForAnEvent(@Context SecurityContext sc, @PathParam("id") String eventId) {
		ExternalUser userMakingRequest = (ExternalUser)sc.getUserPrincipal();
		eventService.unregisterFromAnEvent(userMakingRequest, eventId);
		return Response.noContent().build();
	}
	
	/*@GET
	@Path("pending")
	public Response getPendingEvents(@Context SecurityContext sc) {
		ExternalUser userMakingRequest = (ExternalUser)sc.getUserPrincipal();
		List<EventDTOLite> pendingEvents = eventService.getPendingEvents(userMakingRequest);
		return Response.ok().entity(pendingEvents).build();
	}
	
	@GET
	@Path("registered")
	public Response getEventsToAttend(@Context SecurityContext sc) {
		ExternalUser userMakingRequest = (ExternalUser)sc.getUserPrincipal();
		List<EventDTOLite> eventsToAttend = eventService.getEventsToAttend(userMakingRequest);
		return Response.ok().entity(eventsToAttend).build();
	}*/
	
}