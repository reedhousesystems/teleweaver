package com.reedhousesystems.services.core.health.messenger.api;

import java.util.List;

import com.reedhousesystems.services.core.health.messenger.dto.TopicDTO;
import com.reedhousesystems.services.core.health.messenger.dto.TopicDTOLite;
import com.reedhousesystems.services.core.health.messenger.request.TopicRequest;

public interface TopicService {
	
	public void saveTopic(TopicDTO topic);
	
	public TopicDTOLite getTopicById(String topicId);
	
	public List<TopicRequest> getAllTopics();
}