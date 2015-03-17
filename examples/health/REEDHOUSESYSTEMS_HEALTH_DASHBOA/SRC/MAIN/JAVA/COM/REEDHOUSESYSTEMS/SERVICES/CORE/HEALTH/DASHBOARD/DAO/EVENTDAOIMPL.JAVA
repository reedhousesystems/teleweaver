package com.reedhousesystems.services.core.health.dashboard.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.reedhousesystems.services.core.health.dashboard.api.EventDao;
import com.reedhousesystems.services.core.health.dashboard.exception.ItemNotFoundException;
import com.reedhousesystems.services.core.health.dashboard.model.Event;
import com.reedhousesystems.services.core.health.dashboard.model.Organisation;
import com.reedhousesystems.services.core.health.dashboard.requests.EventRequest;

@Repository("eventDao")
public class EventDaoImpl extends BaseDao implements EventDao {

	public void saveEvent(Event event) throws Exception {	
		try {
			getSession().save(event);
		} catch (HibernateException e) {	
			throw new Exception("An error occured while saving an event", e);
		}
	}
	
	public boolean eventTargetExists(String eventId) throws Exception {
		Event event = getEventById(eventId);
		if (event.getTarget() == null) {
			return false;
		} else {
			return true;
		}
	}

	public void deleteEvent(String eventId) throws Exception {
		Event eventToDelete = getEventById(eventId);
		getSession().delete(eventToDelete);
	}

	public Event getEventById(String eventId) throws Exception {
		Event event = null;
		try {
			Criteria criteria = getSession().createCriteria(Event.class)
				.add(Restrictions.eq("uuid", eventId));
			event = (Event) criteria.uniqueResult();
		} catch (HibernateException e) {	
			throw new Exception("An error occured while getting an event", e);
		}
		if (event == null) throw new ItemNotFoundException("Event Not Found", "Event requested cannot be found");
		return event;
	}

	public List<Event> getAllEvents(Organisation organisation) throws Exception {
		List<Event> eventList = new ArrayList<Event>();
		try {
			Criteria criteria = getSession().createCriteria(Event.class)
				.add(Restrictions.eq("organisation", organisation))
				.addOrder(Order.desc("id"))
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			eventList = criteria.list();
		} catch (HibernateException e) {	
			throw new Exception("An error occured while getting a list of events", e);
		}
		return eventList;
	}
	
}