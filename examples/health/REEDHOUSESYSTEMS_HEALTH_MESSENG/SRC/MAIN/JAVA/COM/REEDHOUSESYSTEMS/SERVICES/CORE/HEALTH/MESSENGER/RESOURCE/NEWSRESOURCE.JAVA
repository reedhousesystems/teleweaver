package com.reedhousesystems.services.core.health.messenger.resource;

import java.util.List;

import javax.annotation.security.PermitAll;
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

import com.reedhousesystems.services.core.health.messenger.api.NewsService;
import com.reedhousesystems.services.core.health.messenger.dto.NewsDTO;
import com.reedhousesystems.services.core.health.messenger.dto.NewsDTOLite;
import com.reedhousesystems.services.core.health.messenger.filter.CorsPreflight;
import com.reedhousesystems.services.core.health.messenger.model.ExternalUser;

@PermitAll
@Path("/new")
@Component
@Produces({MediaType.APPLICATION_JSON})
public class NewsResource {
	
	@Autowired
	private NewsService newsService;
	
	@OPTIONS
	@CorsPreflight
	public Response optionRequest() {
		return Response.ok().build();
	}

	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	public Response saveNews(NewsDTO newsDTO) {
		Integer numberOfMatchingUsers = newsService.saveNews(newsDTO);
		return Response.ok().entity(numberOfMatchingUsers).build();
	}

	@GET
	@Path("{id}")
	public Response getNewsById(@PathParam("id") String newsId, @Context SecurityContext sc) {
        ExternalUser userMakingRequest = (ExternalUser)sc.getUserPrincipal();
        NewsDTOLite news = null;
		try {
			news = newsService.getNewsById(newsId, userMakingRequest);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return Response.ok().entity(news).build();
	}

	@GET
	public Response getActiveNews(@Context SecurityContext sc) {
		ExternalUser userMakingRequest = (ExternalUser)sc.getUserPrincipal();
		List<NewsDTOLite> newsList = newsService.getActiveNews(userMakingRequest);
		return Response.ok().entity(newsList).build();
	}
    
}