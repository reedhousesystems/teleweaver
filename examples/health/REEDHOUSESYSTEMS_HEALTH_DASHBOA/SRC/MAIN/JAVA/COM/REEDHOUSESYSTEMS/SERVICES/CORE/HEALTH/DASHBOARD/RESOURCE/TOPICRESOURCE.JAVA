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

import com.reedhousesystems.services.core.health.dashboard.api.TopicService;
import com.reedhousesystems.services.core.health.dashboard.dto.StatusDTO;
import com.reedhousesystems.services.core.health.dashboard.dto.TopicDTO;
import com.reedhousesystems.services.core.health.dashboard.dto.TopicDetailsListDTO;
import com.reedhousesystems.services.core.health.dashboard.model.Topic;
import com.reedhousesystems.services.core.health.dashboard.requests.TopicDetailsRequest;
import com.reedhousesystems.services.core.health.dashboard.requests.TopicRequest;

@Path("/topics/topics")
@Component
@Produces({MediaType.APPLICATION_JSON})
public class TopicResource {
	
	@Autowired
	private TopicService topicService;

    @Context
    protected UriInfo uriInfo;
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	public Response saveTopic(TopicRequest request) throws Exception {
		TopicRequest savedTopic = null;
		try {
			savedTopic = topicService.saveTopic(request);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
        URI location = uriInfo.getAbsolutePathBuilder().path(savedTopic.getId()).build();
        return Response.created(location).entity(savedTopic).build();
	}
	
	@PUT
	@Path("{id}")
	@Consumes({MediaType.APPLICATION_JSON})
	public Response updateTopic(@PathParam("id") String topicId, TopicRequest request) throws Exception {
		try {
			topicService.updateTopic(topicId, request);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return Response.noContent().build();
	}
	
	@PUT
	@Path("{id}/status")
	@Consumes({MediaType.APPLICATION_JSON})
	public Response updateTopic(@PathParam("id") String topicId, StatusDTO statusDTO) throws Exception {
		try {
			topicService.updateTopic(topicId, statusDTO);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return Response.noContent().build();
	}
	
	@DELETE
	@Path("{id}")
	public Response deleteTopic(@PathParam("id") String topicId) throws Exception {
		try {
			topicService.deleteTopic(topicId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return Response.noContent().build();
	}
	
	@GET
	@Path("{id}")
	public Response getTopicById(@PathParam("id") String topicId) throws Exception {
		TopicDTO topic = null;
		try {
			topic = topicService.getTopicById(topicId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return Response.ok().entity(topic).build();
	}
	
	@GET
	public Response getTopicList() throws Exception {
		List<Topic> topicList = new ArrayList<Topic>();
		try {
			topicList = topicService.getTopicList();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return Response.ok().entity(topicList).build();
	}
	
	@POST
	@Path("{id}/details")
	@Consumes({MediaType.APPLICATION_JSON})
	public Response saveTopicDetails(@PathParam("id") String topicId, TopicDetailsRequest request) throws Exception {
		TopicDetailsRequest savedTopicDetails = null;
		try {
			savedTopicDetails = topicService.saveTopicDetails(topicId, request);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return Response.ok().entity(savedTopicDetails).build();
	}
	
	@PUT
	@Path("{topcId}/details/{id}")
	@Consumes({MediaType.APPLICATION_JSON})
	public Response updateTopicDetails(@PathParam("topcId") String topicId, @PathParam("id") String topicDetailsId, TopicDetailsRequest request) throws Exception {
		TopicDetailsListDTO topicDetailsListDTO = null;
		try {
			topicDetailsListDTO = topicService.updateTopicDetails(topicId, topicDetailsId, request);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return Response.ok().entity(topicDetailsListDTO).build();
	}
	
	@DELETE
	@Path("{topicId}/details/{detailsId}")
	public Response deleteTopicDetail(@PathParam("topicId") String topicId, @PathParam("detailsId") String topicDetailsId) throws Exception {
		try {
			topicService.deleteTopicDetail(topicDetailsId, topicId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return Response.noContent().build();
	}
	
	@GET
	@Path("{topicId}/details/{id}")
	public Response getTopicDetailsById(@PathParam("id") String topicDetailsId) throws Exception {
		TopicDetailsRequest topicDetails = null;
		try {
			topicDetails = topicService.getTopicDetailsById(topicDetailsId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return Response.ok().entity(topicDetails).build();
	}
	
	@GET
	@Path("{id}/details")
	public Response getTopicDetailsList(@PathParam("id") String topicId) throws Exception {
		List<TopicDetailsRequest> topicDetailsList = new ArrayList<TopicDetailsRequest>();
		try {
			topicDetailsList = topicService.getTopicDetailsList(topicId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return Response.ok().entity(topicDetailsList).build();
	}
	
}