package com.reedhousesystems.services.core.health.dashboard.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reedhousesystems.services.core.health.dashboard.api.EventDao;
import com.reedhousesystems.services.core.health.dashboard.api.EventService;
import com.reedhousesystems.services.core.health.dashboard.api.LocationDao;
import com.reedhousesystems.services.core.health.dashboard.api.OrganisationDao;
import com.reedhousesystems.services.core.health.dashboard.api.TargetDao;
import com.reedhousesystems.services.core.health.dashboard.api.UserDao;
import com.reedhousesystems.services.core.health.dashboard.dto.EventDTO;
import com.reedhousesystems.services.core.health.dashboard.dto.EventDTOLite;
import com.reedhousesystems.services.core.health.dashboard.dto.TargetDTO;
import com.reedhousesystems.services.core.health.dashboard.dto.UUIDDTO;
import com.reedhousesystems.services.core.health.dashboard.exception.ItemNotFoundException;
import com.reedhousesystems.services.core.health.dashboard.model.Event;
import com.reedhousesystems.services.core.health.dashboard.model.ExternalUser;
import com.reedhousesystems.services.core.health.dashboard.model.Location;
import com.reedhousesystems.services.core.health.dashboard.model.Organisation;
import com.reedhousesystems.services.core.health.dashboard.model.Target;
import com.reedhousesystems.services.core.health.dashboard.requests.EventRequest;

@Service("eventService")
@Transactional
public class EventServiceImpl implements EventService {
	
	@Resource
	private EventDao eventDao;

    @Resource
    private UserDao userDao;
	
    @Resource
    private OrganisationDao organisationDao;
    
    @Resource
	private TargetDao targetDao;
    
    @Resource
    private LocationDao locationDao;
	
	public UUIDDTO saveEvent(EventRequest request) throws Exception {
		String UUID = null;
		try {
			Event newEvent = createNewEvent(request);
			UUID = newEvent.getUuid().toString(); 
			eventDao.saveEvent(newEvent);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new UUIDDTO(UUID);
	}

	public void updateEvent(String eventId, EventRequest request) throws Exception {
		try {
			Event eventToUpdate = eventDao.getEventById(eventId);
			if (request.getName() != null) eventToUpdate.setName(request.getName());
			if (request.getDescription() != null) eventToUpdate.setDescription(request.getDescription());
			if (request.getStartDate() != null) eventToUpdate.setStartDate(request.getStartDate());
			if (request.getEndDate() != null) eventToUpdate.setEndDate(request.getEndDate());
			if (request.getTarget() != null) setEventTarget(eventId, new TargetDTO(request.getTarget()));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void updateEvent(String eventId, String status) throws Exception {
		try {
			Event eventToUpdate = eventDao.getEventById(eventId);
			eventToUpdate.setStatus(status);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void setEventTarget(String eventId, TargetDTO targetDTO) throws Exception {
		try {
			Event eventToUpdate = eventDao.getEventById(eventId);
			if (eventToUpdate == null)
				throw new ItemNotFoundException("Event not found", "The event to set target cannot be found");
			Location location = locationDao.getLocation(targetDTO.getLocationDTO());
			Target targetToCheck = new Target(targetDTO);
			targetToCheck.setLocation(location);
			Target target = targetDao.getTarget(targetToCheck);
			if (target == null)
				target = targetDao.saveTarget(targetToCheck);
			eventToUpdate.setTarget(target);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public TargetDTO getEventTarget(String eventId) throws Exception {
		Target target = null;
		try {
			Event event = eventDao.getEventById(eventId);
			target = event.getTarget();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new TargetDTO(target);
	}

	public EventDTOLite getEventById(String eventId) throws Exception {
		Event event = null;
		try {
			event = eventDao.getEventById(eventId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new EventDTOLite(event);
	}

	public EventDTO getEventToPublish(String eventId) throws Exception {
		Event event = null;
		try {
			event = eventDao.getEventById(eventId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new EventDTO(event);
	}

	public void deleteEvent(String eventId) throws Exception {
		try {
			eventDao.deleteEvent(eventId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public List<EventDTOLite> getAllEvents(ExternalUser userMakingRequest) throws Exception {
		Organisation organisation = organisationDao.getOrganisationById(userMakingRequest.getOrganisation());
		List<Event> events = new ArrayList<Event>();
		List<EventDTOLite> eventList = new ArrayList<EventDTOLite>();
		try {
			events = eventDao.getAllEvents(organisation);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		for (Event event : events) {
			EventDTOLite eventDTOLite = new EventDTOLite(event);
			eventList.add(eventDTOLite);
		}
		return eventList;
	}
	
	private Event createNewEvent(EventRequest request) {
		Event newEvent = new Event(request);
		Organisation organisation = userDao.getOrganisationByUserId(request.getUserId());
		newEvent.setOrganisation(organisation);
		Date now = new Date();
		newEvent.setCreationDate(now);
		newEvent.setStatus("Active");
		return newEvent;
	}
	
}