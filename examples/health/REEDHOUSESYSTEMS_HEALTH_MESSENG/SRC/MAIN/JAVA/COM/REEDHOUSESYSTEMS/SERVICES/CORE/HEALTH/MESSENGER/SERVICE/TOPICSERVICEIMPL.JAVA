package com.reedhousesystems.services.core.health.messenger.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reedhousesystems.services.core.health.messenger.api.TopicDao;
import com.reedhousesystems.services.core.health.messenger.api.TopicService;
import com.reedhousesystems.services.core.health.messenger.dto.TopicDTO;
import com.reedhousesystems.services.core.health.messenger.dto.TopicDTOLite;
import com.reedhousesystems.services.core.health.messenger.dto.TopicDetailsDTO;
import com.reedhousesystems.services.core.health.messenger.model.Topic;
import com.reedhousesystems.services.core.health.messenger.model.TopicDetails;
import com.reedhousesystems.services.core.health.messenger.request.TopicRequest;

@Service("topicService")
@Transactional
public class TopicServiceImpl implements TopicService {
	
	private TopicDao topicDao;

	@Autowired
	public TopicServiceImpl(TopicDao topicDao) {
		this.topicDao = topicDao;
	}

	public void saveTopic(TopicDTO topic) {
		Topic topicToUpdate = topicDao.getTopicById(topic.getId());
		if (topicToUpdate != null) {
			topicToUpdate.setTitle(topic.getTitle());
			topicToUpdate.setDescription(topic.getDescription());
			topicToUpdate.setLanguage(topic.getLanguage());
			topicToUpdate.setStatus(topic.getStatus());
			List<TopicDetails> topicDetails = new ArrayList<TopicDetails>();
			for (TopicDetailsDTO topicDetailsDTO : topic.getTopicDetailsDTOList()) {
				topicDetails.add(new TopicDetails(topicDetailsDTO));
			}
			topicToUpdate.setTopicDetails(topicDetails);
		} else {
			topicDao.saveTopic(new Topic(topic));
		}
	}

	public TopicDTOLite getTopicById(String topicId) {
		Topic topic = topicDao.getTopicById(topicId);
		return new TopicDTOLite(topic);
	}

	public List<TopicRequest> getAllTopics() {
		List<Topic> topicList = topicDao.getAllTopics();
		List<TopicRequest> topics = new ArrayList<TopicRequest>();
		for (Topic topic : topicList) {
			topics.add(new TopicRequest(topic));
		}
		return topics;
	}
}