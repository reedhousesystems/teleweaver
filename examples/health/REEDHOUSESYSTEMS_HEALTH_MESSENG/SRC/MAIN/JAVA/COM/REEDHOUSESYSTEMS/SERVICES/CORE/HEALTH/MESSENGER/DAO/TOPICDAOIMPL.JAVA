package com.reedhousesystems.services.core.health.messenger.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.reedhousesystems.services.core.health.messenger.api.TopicDao;
import com.reedhousesystems.services.core.health.messenger.model.Topic;

@Repository("topicDao")
public class TopicDaoImpl extends BaseDao implements TopicDao {

	public void saveTopic(Topic topic) {
		getSession().save(topic);
	}

	public Topic getTopicById(String topicId) {
		Criteria criteria = getSession().createCriteria(Topic.class)
			.add(Restrictions.eq("uuid", topicId));
		Topic topic = (Topic) criteria.uniqueResult();
		return topic;
	}

	public List<Topic> getAllTopics() {
		List<Topic> topicList = null;
		Criteria criteria = getSession().createCriteria(Topic.class)
			.add(Restrictions.eq("status", "Active"))
			.addOrder(Order.desc("creationDate"))
			.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		topicList = criteria.list();
		return topicList;
	}
	
}