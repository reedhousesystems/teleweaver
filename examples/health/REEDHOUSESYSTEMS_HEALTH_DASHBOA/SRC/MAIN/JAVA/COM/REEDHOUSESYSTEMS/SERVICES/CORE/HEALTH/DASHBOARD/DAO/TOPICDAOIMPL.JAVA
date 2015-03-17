package com.reedhousesystems.services.core.health.dashboard.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.reedhousesystems.services.core.health.dashboard.api.TopicDao;
import com.reedhousesystems.services.core.health.dashboard.exception.ItemNotFoundException;
import com.reedhousesystems.services.core.health.dashboard.model.Topic;
import com.reedhousesystems.services.core.health.dashboard.model.TopicDetails;
import com.reedhousesystems.services.core.health.dashboard.requests.TopicDetailsRequest;
import com.reedhousesystems.services.core.health.dashboard.requests.TopicRequest;

@Repository("topicDao")
public class TopicDaoImpl extends BaseDao implements TopicDao {

	//A function to add a fact sheet in the database 
	public TopicRequest saveTopic(Topic topic) throws Exception {
		try {
			Long topicId = (Long) getSession().save(topic);
			topic.setId(topicId);
		} catch (HibernateException hex) {
			throw new Exception("An error occurred while saving a fact sheet", hex);
		}
		return new TopicRequest(topic);
	}

	//A function to delete a fact sheet
	public void deleteTopic(String topicId) throws Exception {
		try {
			Topic topicToDelete = getTopicById(topicId);
			getSession().delete(topicToDelete);
		} catch (HibernateException hex) {
			throw new Exception("An error occured while deleting a fact sheet", hex);
		}
	}

	//A function to get a fact sheet by Id
	public Topic getTopicById(String topicId) throws Exception {
		Topic topic = null;
		try {
			Criteria criteria = getSession().createCriteria(Topic.class)
				.add(Restrictions.eq("uuid", topicId));
			topic = (Topic) criteria.uniqueResult();
		} catch (HibernateException hex) {
			throw new Exception("An error occurred while getting a topic", hex);
		}
		if (topic == null) throw new ItemNotFoundException("Topic Not Found", "Topic requested cannot be found");
		return topic;
	}

	//A function to get a list of fact sheets
	public List<Topic> getTopicList() throws Exception {
		List<Topic> topicList = new ArrayList<Topic>();
		try {
			Criteria criteria = getSession().createCriteria(Topic.class)
				.setProjection(Projections.distinct(Projections.projectionList()
					.add(Projections.property("title"))
					.add(Projections.property("creationDate"))
					.add(Projections.property("status"))
					.add(Projections.property("uuid"))));
			topicList = criteria.list();
		} catch (HibernateException hex) {	
			throw new Exception("An error occurred while getting a list of fact sheets", hex);
		}
		return topicList;
	}

	public TopicDetailsRequest saveTopicDetails(String topicId, TopicDetails topicDetails) throws Exception {
		Topic topic = getTopicById(topicId);
		try {
			topicDetails.setTopic(topic);
			getSession().save(topicDetails);
		} catch (HibernateException hex) {
			throw new Exception("An error occured while saving topic details", hex);
		}
		return new TopicDetailsRequest(topicDetails);
	}

	public void deleteTopicDetail(String topicDetailsId, String topicId) throws Exception {
		TopicDetails topicDetailsToDelete = getTopicDetailsById(topicDetailsId);
		Topic topic = getTopicById(topicId);
		try {
			topic.getTopicDetails().remove(topicDetailsToDelete);
			getSession().delete(topicDetailsToDelete);
		} catch (HibernateException hex) {
			throw new Exception("An error occurred while deleting facts sheet details", hex);
		}
	}

	public TopicDetails getTopicDetailsById(String topicDetailsId)
			throws Exception {
		TopicDetails topicDetails = null;
		try {
			Criteria criteria = getSession().createCriteria(TopicDetails.class)
				.add(Restrictions.eq("uuid", topicDetailsId));
			topicDetails = (TopicDetails) criteria.uniqueResult();
		} catch (HibernateException hex) {
			throw new Exception("An error occurred while getting topic details", hex);
		}
		if (topicDetails == null) throw new ItemNotFoundException("Topic details not found", "Topic details requested cannot be found");
		return topicDetails;
	}
}