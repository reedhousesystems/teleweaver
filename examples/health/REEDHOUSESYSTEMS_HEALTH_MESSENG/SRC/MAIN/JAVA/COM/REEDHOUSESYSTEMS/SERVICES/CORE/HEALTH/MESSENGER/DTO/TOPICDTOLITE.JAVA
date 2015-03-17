package com.reedhousesystems.services.core.health.messenger.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.reedhousesystems.services.core.health.messenger.model.Topic;
import com.reedhousesystems.services.core.health.messenger.model.TopicDetails;

public class TopicDTOLite {
	
	@NotNull
	private String title;
	
	private List<TopicDetailsDTOLite> detailsDTOLite = new ArrayList<TopicDetailsDTOLite>();
	
	public TopicDTOLite() {
		
	}
	
	public TopicDTOLite(Topic topic) {
		this.title = topic.getTitle();
		for (TopicDetails topicDetails : topic.getTopicDetails()) {
			detailsDTOLite.add(new TopicDetailsDTOLite(topicDetails));
		}
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<TopicDetailsDTOLite> getTopicDetailsDTOList() {
		return detailsDTOLite;
	}

	public void setTopicDetailsDTOList(List<TopicDetailsDTOLite> detailsDTOLiteList) {
		this.detailsDTOLite = detailsDTOLiteList;
	}
	
}