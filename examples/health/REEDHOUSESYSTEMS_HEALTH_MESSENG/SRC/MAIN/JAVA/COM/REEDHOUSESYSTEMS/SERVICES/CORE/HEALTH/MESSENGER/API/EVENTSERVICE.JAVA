package com.reedhousesystems.services.core.health.messenger.api;

import java.util.List;

import com.reedhousesystems.services.core.health.messenger.dto.EventDTO;
import com.reedhousesystems.services.core.health.messenger.dto.EventDTOLite;
import com.reedhousesystems.services.core.health.messenger.model.ExternalUser;

public interface EventService {
	
	public void saveEvent(EventDTO event);
	
	public void registerForAnEvent(ExternalUser userMakingRequest, String eventId);
	
	public void unregisterFromAnEvent(ExternalUser userMakingRequest, String eventId);
	
	public List<EventDTOLite> getAllEvents(ExternalUser userMakingRequest);
	
}