package com.reedhousesystems.services.core.health.dashboard.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reedhousesystems.services.core.health.dashboard.api.TopicDao;
import com.reedhousesystems.services.core.health.dashboard.api.TopicService;
import com.reedhousesystems.services.core.health.dashboard.dto.StatusDTO;
import com.reedhousesystems.services.core.health.dashboard.dto.TopicDTO;
import com.reedhousesystems.services.core.health.dashboard.dto.TopicDetailsListDTO;
import com.reedhousesystems.services.core.health.dashboard.exception.ItemNotFoundException;
import com.reedhousesystems.services.core.health.dashboard.model.Topic;
import com.reedhousesystems.services.core.health.dashboard.model.TopicDetails;
import com.reedhousesystems.services.core.health.dashboard.requests.TopicDetailsRequest;
import com.reedhousesystems.services.core.health.dashboard.requests.TopicRequest;

@Service("topicService")
@Transactional
public class TopicServiceImpl implements TopicService {
	
	@Resource
	private TopicDao topicDao;
	
	public TopicRequest saveTopic(TopicRequest request) throws Exception {
		TopicRequest savedTopic = null;
		try {
			Topic newTopic = createNewTopic(request);
			savedTopic = topicDao.saveTopic(newTopic);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return savedTopic;
	}

	public void updateTopic(String topicId, TopicRequest request) throws Exception {
		try {
			Topic topicToUpdate = topicDao.getTopicById(topicId);
			if (request.getTitle() != null)
				topicToUpdate.setTitle(request.getTitle());
			if (request.getDescription() != null)
				topicToUpdate.setDescription(request.getDescription());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void updateTopic(String topicId, StatusDTO statusDTO) throws Exception {
		Topic topicToUpdate = topicDao.getTopicById(topicId);
		topicToUpdate.setStatus(statusDTO.getStatus());
	}

	public void deleteTopic(String topicId) throws Exception {
		try {
			topicDao.deleteTopic(topicId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public TopicDTO getTopicById(String topicId) throws Exception {
		Topic topic = null;
		try {
			topic = topicDao.getTopicById(topicId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new TopicDTO(topic);
	}

	public List<Topic> getTopicList() throws Exception {
		List<Topic> topicList = new ArrayList<Topic>();
		try {
			topicList = topicDao.getTopicList();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return topicList;
	}

	public TopicDetailsRequest saveTopicDetails(String topicId, TopicDetailsRequest request) throws Exception {
		TopicDetailsRequest newTopicDetails = null;
		try {
			TopicDetails topicDetails = new TopicDetails(request);
			newTopicDetails = topicDao.saveTopicDetails(topicId, topicDetails);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return newTopicDetails;
	}

	public TopicDetailsListDTO updateTopicDetails(String topicId, String topicDetailsId, TopicDetailsRequest request) throws Exception {
		Topic topic = null;
		try {
			TopicDetails topicDetails = topicDao.getTopicDetailsById(topicDetailsId);
			if (topicDetails == null)
				throw new ItemNotFoundException("Topic detail not found", "The topic details requested to be edited cannot be found");
			topicDetails.setSectionName(request.getSectionName());
			topicDetails.setSectionDescription(request.getSectionDescription());
			topic = topicDao.getTopicById(topicId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new TopicDetailsListDTO(topic);
	}

	public void deleteTopicDetail(String topicDetailsId, String topicId) throws Exception {
		try {
			topicDao.deleteTopicDetail(topicDetailsId, topicId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public TopicDetailsRequest getTopicDetailsById(String topicDetailsId) throws Exception {
		TopicDetails topicDetails = null;
		try {
			topicDetails = topicDao.getTopicDetailsById(topicDetailsId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new TopicDetailsRequest(topicDetails);
	}

	public List<TopicDetailsRequest> getTopicDetailsList(String topicId) throws Exception {	
		List<TopicDetails> topicDetailsList = new ArrayList<TopicDetails>();	
		List<TopicDetailsRequest> topicDetailsRequestList = new ArrayList<TopicDetailsRequest>();
		try {
			Topic topic = topicDao.getTopicById(topicId);
			topicDetailsList = topic.getTopicDetails();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		for (TopicDetails topicDetails : topicDetailsList) {
			topicDetailsRequestList.add(new TopicDetailsRequest(topicDetails));
		}
		return topicDetailsRequestList;
	}
	
	private Topic createNewTopic(TopicRequest request) {
		Date creationDate = new Date();
		Topic newTopic = new Topic(request);
		newTopic.setCreationDate(creationDate);
		newTopic.setStatus("Active");
		return newTopic;
	}
	
}