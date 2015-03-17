package com.reedhousesystems.services.core.health.dashboard.api;

import java.util.List;

import com.reedhousesystems.services.core.health.dashboard.model.Event;
import com.reedhousesystems.services.core.health.dashboard.model.Organisation;

public interface EventDao {
	
	public void saveEvent(Event event) throws Exception;
	
	public boolean eventTargetExists(String eventId) throws Exception;
	
	public void deleteEvent(String eventId) throws Exception;
	
	public Event getEventById(String eventId) throws Exception;
	
	public List<Event> getAllEvents(Organisation organisation) throws Exception;
	
}