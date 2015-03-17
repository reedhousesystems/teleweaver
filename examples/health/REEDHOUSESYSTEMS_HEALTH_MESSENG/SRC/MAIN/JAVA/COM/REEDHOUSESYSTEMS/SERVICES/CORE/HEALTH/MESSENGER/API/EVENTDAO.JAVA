package com.reedhousesystems.services.core.health.messenger.api;

import java.util.List;

import com.reedhousesystems.services.core.health.messenger.model.Event;

public interface EventDao {
	
	public void saveEvent(Event event);
	
	public Event getEventById(String eventId);
	
	public List<Event> getAllEvents();
	
}