package com.reedhousesystems.services.core.health.dashboard.api;

import java.util.List;

import com.reedhousesystems.services.core.health.dashboard.dto.StatusDTO;
import com.reedhousesystems.services.core.health.dashboard.dto.TopicDTO;
import com.reedhousesystems.services.core.health.dashboard.dto.TopicDetailsListDTO;
import com.reedhousesystems.services.core.health.dashboard.model.Topic;
import com.reedhousesystems.services.core.health.dashboard.requests.TopicDetailsRequest;
import com.reedhousesystems.services.core.health.dashboard.requests.TopicRequest;

public interface TopicService {
	
	public TopicRequest saveTopic(TopicRequest request) throws Exception;
	
	public void updateTopic(String topicId, TopicRequest request) throws Exception;
	
	public void updateTopic(String topicId, StatusDTO statusDTO) throws Exception;
	
	public void deleteTopic(String topicId) throws Exception;
	
	public TopicDTO getTopicById(String topicId) throws Exception;
	
	public List<Topic> getTopicList() throws Exception;
	
	public TopicDetailsRequest saveTopicDetails(String topicId, TopicDetailsRequest request) throws Exception;
	
	public TopicDetailsListDTO updateTopicDetails(String topicId, String topicDetailsId, TopicDetailsRequest request) throws Exception;
	
	public void deleteTopicDetail(String topicDetailsId, String topicId) throws Exception;
	
	public TopicDetailsRequest getTopicDetailsById(String topicDetailsId) throws Exception;
	
	public List<TopicDetailsRequest> getTopicDetailsList(String topicId) throws Exception;
	
}