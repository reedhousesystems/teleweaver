package com.reedhousesystems.services.core.health.messenger.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.reedhousesystems.services.core.health.messenger.api.EventDao;
import com.reedhousesystems.services.core.health.messenger.model.Event;

@Repository("EventDao")
public class EventDaoImpl extends BaseDao implements EventDao {

	public void saveEvent(Event event) {
		getSession().save(event);
	}

	public Event getEventById(String eventId) {
		Criteria criteria = getSession().createCriteria(Event.class)
			.add(Restrictions.eq("uuid", eventId));
		Event event = (Event) criteria.uniqueResult();
		return event;
	}

	public List<Event> getAllEvents() {
		Criteria criteria = getSession().createCriteria(Event.class)
			.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<Event> events = criteria.list();
		return events;
	}	
}