package com.reedhousesystems.services.core.health.dashboard.dto;

import java.util.ArrayList;
import java.util.List;

import com.reedhousesystems.services.core.health.dashboard.model.Topic;
import com.reedhousesystems.services.core.health.dashboard.model.TopicDetails;
import com.reedhousesystems.services.core.health.dashboard.requests.TopicDetailsRequest;

public class TopicDetailsListDTO {
	
	private String id;
	
	private List<TopicDetailsRequest> topicDetailsList = new ArrayList<TopicDetailsRequest>();
	
	public TopicDetailsListDTO() {}
	
	public TopicDetailsListDTO(Topic topic) {
		this.id = topic.getUuid().toString();
		for (TopicDetails topicDetails : topic.getTopicDetails()) {
			topicDetailsList.add(new TopicDetailsRequest(topicDetails));
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public List<TopicDetailsRequest> getTopicDetailsList() {
		return topicDetailsList;
	}
	
	public void setTopicDetailsList(List<TopicDetailsRequest> topicDetailsList) {
		this.topicDetailsList = topicDetailsList;
	}
}