package com.reedhousesystems.services.core.health.messenger.api;

import java.util.List;

import com.reedhousesystems.services.core.health.messenger.model.Topic;

public interface TopicDao {
	
	public void saveTopic(Topic topic);
	
	public Topic getTopicById(String topicId);
	
	public List<Topic> getAllTopics();
	
}