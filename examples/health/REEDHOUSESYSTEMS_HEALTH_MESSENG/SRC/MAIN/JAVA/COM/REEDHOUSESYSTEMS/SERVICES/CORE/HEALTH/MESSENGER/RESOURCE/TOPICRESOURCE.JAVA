package com.reedhousesystems.services.core.health.messenger.resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.reedhousesystems.services.core.health.messenger.api.TopicService;
import com.reedhousesystems.services.core.health.messenger.dto.TopicDTO;
import com.reedhousesystems.services.core.health.messenger.dto.TopicDTOLite;
import com.reedhousesystems.services.core.health.messenger.request.TopicRequest;

@Path("/topics")
@Component
@Produces({MediaType.APPLICATION_JSON})
public class TopicResource {

	@Autowired
	private TopicService topicService;
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	public void saveFactSheet(TopicDTO topic) {
		topicService.saveTopic(topic);
	}
	
	@GET
	public Response getTopicList() {
		if (topicService == null) {
			System.out.println("Topic service is null");
		}
		List<TopicRequest> topicList = topicService.getAllTopics();
		return Response.ok().entity(topicList).build();
	}
	
	@GET
	@Path("{id}")
	public Response getTopicById(@PathParam("id") String topicId) {
		TopicDTOLite topic = topicService.getTopicById(topicId);
		return Response.ok().entity(topic).build();
	}
}