package com.reedhousesystems.services.core.health.dashboard.api;

import java.util.List;

import com.reedhousesystems.services.core.health.dashboard.dto.EventDTO;
import com.reedhousesystems.services.core.health.dashboard.dto.EventDTOLite;
import com.reedhousesystems.services.core.health.dashboard.dto.TargetDTO;
import com.reedhousesystems.services.core.health.dashboard.dto.UUIDDTO;
import com.reedhousesystems.services.core.health.dashboard.model.ExternalUser;
import com.reedhousesystems.services.core.health.dashboard.requests.EventRequest;

public interface EventService {
	
	public UUIDDTO saveEvent(EventRequest request) throws Exception;
	
	public void updateEvent(String eventId, EventRequest request) throws Exception;
	
	public void updateEvent(String eventId, String status) throws Exception;
	
	public void setEventTarget(String eventId, TargetDTO target) throws Exception;
	
	public TargetDTO getEventTarget(String eventId) throws Exception;
	
	public void deleteEvent(String eventId) throws Exception;
	
	public EventDTOLite getEventById(String eventId) throws Exception;
	
	public EventDTO getEventToPublish(String eventId) throws Exception;
	
	public List<EventDTOLite> getAllEvents(ExternalUser userMakingRequest) throws Exception;
	
}