package com.reedhousesystems.services.core.health.dashboard.api;

import java.util.List;

import com.reedhousesystems.services.core.health.dashboard.model.Topic;
import com.reedhousesystems.services.core.health.dashboard.model.TopicDetails;
import com.reedhousesystems.services.core.health.dashboard.requests.TopicDetailsRequest;
import com.reedhousesystems.services.core.health.dashboard.requests.TopicRequest;

public interface TopicDao {
	
	public TopicRequest saveTopic(Topic topic) throws Exception;
	
	public void deleteTopic(String topicId) throws Exception;
	
	public Topic getTopicById(String topicId) throws Exception;
	
	public List<Topic> getTopicList() throws Exception;
	
	public TopicDetailsRequest saveTopicDetails(String topicId, TopicDetails topicDetails) throws Exception;
	
	public void deleteTopicDetail(String topicDetailsId, String topicId) throws Exception;
	
	public TopicDetails getTopicDetailsById(String topicDetailsId) throws Exception;
	
}