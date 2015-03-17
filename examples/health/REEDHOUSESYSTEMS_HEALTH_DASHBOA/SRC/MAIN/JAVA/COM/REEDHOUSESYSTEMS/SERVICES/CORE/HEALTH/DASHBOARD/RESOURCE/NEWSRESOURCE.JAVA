package com.reedhousesystems.services.core.health.dashboard.resource;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

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
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.reedhousesystems.services.core.health.dashboard.api.NewsService;
import com.reedhousesystems.services.core.health.dashboard.dto.NewsDTO;
import com.reedhousesystems.services.core.health.dashboard.dto.StatusDTO;
import com.reedhousesystems.services.core.health.dashboard.dto.TargetDTO;
import com.reedhousesystems.services.core.health.dashboard.model.Target;
import com.reedhousesystems.services.core.health.dashboard.requests.NewsRequest;

@Path("/news/news")
@Component
@Produces({MediaType.APPLICATION_JSON})
public class NewsResource {
	
	@Autowired
	private NewsService newsService;

    @Context
    protected UriInfo uriInfo;
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	public Response saveNews(NewsRequest request) {
		NewsRequest savedNews = null;
		try {
			savedNews = newsService.saveNews(request);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
        URI location = uriInfo.getAbsolutePathBuilder().path(savedNews.getId()).build();
        return Response.created(location).entity(savedNews).build();
	}

	@PUT
	@Path("{id}")
	@Consumes({MediaType.APPLICATION_JSON})
	public Response updateNews(@PathParam("id") String newsId, NewsRequest request) {
		try {
			newsService.updateNews(newsId, request);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return Response.noContent().build();
	}

	@PUT
	@Path("{id}/status")
	@Consumes({MediaType.APPLICATION_JSON})
	public Response updateNews(@PathParam("id") String newsId, StatusDTO statusDTO) {
		try {
			newsService.updateNews(newsId, statusDTO);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return Response.noContent().build();
	}

	@DELETE
	@Path("{id}")
	public Response deleteNews(@PathParam("id") String newsId) {
		try {
			newsService.deleteNews(newsId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return Response.noContent().build();
	}

	@GET
	@Path("{id}")
	public Response getNewsById(@PathParam("id") String newsId) {
		NewsRequest news = null;
		try {
			news = newsService.getNewsById(newsId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return Response.ok().entity(news).build();
	}

	@GET
	@Path("{id}/publish")
	public Response getNewsToPublish(@PathParam("id") String newsId) {
		NewsDTO newsDTO = null;
		try {
			newsDTO = newsService.getNewsToPublish(newsId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return Response.ok().entity(newsDTO).build();
	}

	@GET
	public Response getAllNews() {
		List<NewsDTO> newsList = new ArrayList<NewsDTO>();
		try {
			newsList = newsService.getAllNews();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return Response.ok().entity(newsList).build();
	}
	
	@PUT
	@Path("{id}/target")
	@Consumes({MediaType.APPLICATION_JSON})
	public Response setNewsTarget(@PathParam("id") String newsId, TargetDTO target) throws Exception {
		try {
			newsService.setNewsTarget(newsId, target);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return Response.ok().build();
	}

	@GET
	@Path("{id}/target")
	public Response getNewsTarget(@PathParam("id") String newsId) throws Exception {
		Target target = null;
		try {
			target = newsService.getNewsTarget(newsId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (target == null)
			return Response.noContent().build();
		else
			return Response.ok().entity(target).build();
	}
	
}