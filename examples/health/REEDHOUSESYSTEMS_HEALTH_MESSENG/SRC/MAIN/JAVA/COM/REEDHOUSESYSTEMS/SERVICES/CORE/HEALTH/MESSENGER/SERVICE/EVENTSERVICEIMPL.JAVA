package com.reedhousesystems.services.core.health.messenger.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reedhousesystems.services.core.health.messenger.api.EventDao;
import com.reedhousesystems.services.core.health.messenger.api.EventService;
import com.reedhousesystems.services.core.health.messenger.api.LocationDao;
import com.reedhousesystems.services.core.health.messenger.api.LogDao;
import com.reedhousesystems.services.core.health.messenger.api.OrganisationDao;
import com.reedhousesystems.services.core.health.messenger.api.TargetDao;
import com.reedhousesystems.services.core.health.messenger.api.UserDao;
import com.reedhousesystems.services.core.health.messenger.dto.EventDTO;
import com.reedhousesystems.services.core.health.messenger.dto.EventDTOLite;
import com.reedhousesystems.services.core.health.messenger.exception.ItemNotFoundException;
import com.reedhousesystems.services.core.health.messenger.model.Event;
import com.reedhousesystems.services.core.health.messenger.model.ExternalUser;
import com.reedhousesystems.services.core.health.messenger.model.Location;
import com.reedhousesystems.services.core.health.messenger.model.Log;
import com.reedhousesystems.services.core.health.messenger.model.Organisation;
import com.reedhousesystems.services.core.health.messenger.model.Target;
import com.reedhousesystems.services.core.health.messenger.model.User;

@Service("eventService")
@Transactional
public class EventServiceImpl extends BaseService implements EventService {
	
	@Autowired
	private EventDao eventDao;
	
	@Autowired
	private UserDao userDao;
	
	@Resource
	private OrganisationDao organisationDao;

	@Autowired
	private LocationDao locationDao;

	@Autowired
	private TargetDao targetDao;
	
	private LogDao logDao;
	
	@Resource
	protected void setLogDao(LogDao logDao) {
		this.logDao = logDao;
	}	
	
	public void saveEvent(EventDTO eventDTO) {
		Event eventToUpdate = eventDao.getEventById(eventDTO.getId());
		if (eventToUpdate != null) {
			Organisation organisation = organisationDao.getOrganisation(eventDTO.getOrganisation());
			if (organisation == null) {
				throw new ItemNotFoundException("Organisation not found", "Your organisation was not found. Please contact the system administrator to register your organisation");
			}
			eventToUpdate.setName(eventDTO.getName());
			eventToUpdate.setDescription(eventDTO.getDescription());
			eventToUpdate.setStartDate(eventDTO.getStartDate());
			eventToUpdate.setEndDate(eventDTO.getEndDate());
			eventToUpdate.setStatus(eventDTO.getStatus());
			Log log = logDao.getLog(eventDTO.getId());
			log.setName(eventDTO.getName());
			log.setStartDate(eventDTO.getStartDate());
			log.setEndDate(eventDTO.getEndDate());
			Target target = targetDao.getTarget(eventDTO.getTargetDTO().getId());
			if (target != null) {
				eventToUpdate.setTarget(target);
				log.setTarget(target);
			} else {
				Target targetToSet = getTargetToSet(eventDTO);
				eventToUpdate.setTarget(targetToSet);
				log.setTarget(targetToSet);
			}
		} else {
			Log logToSave = null;
			Organisation organisation = organisationDao.getOrganisation(eventDTO.getOrganisation());
			if (organisation == null) {
				throw new ItemNotFoundException("Organisation not found", "Your organisation was not found. Please contact the system administrator to register your organisation");
			}
			Event event = new Event(eventDTO);
			Target target = targetDao.getTarget(eventDTO.getTargetDTO().getId());
			if (target != null) {
				event.setTarget(target);
				logToSave = new Log(eventDTO.getId(), eventDTO.getName(), "events", eventDTO.getOrganisation(), eventDTO.getStartDate(), eventDTO.getEndDate(), target);
			} else {
				Target targetToSet = getTargetToSet(eventDTO);
				event.setTarget(targetToSet);
				logToSave = new Log(eventDTO.getId(), eventDTO.getName(), "events", eventDTO.getOrganisation(), eventDTO.getStartDate(), eventDTO.getEndDate(), targetToSet);
			}
			event.setOrganisation(organisation);
			eventDao.saveEvent(event);
			logDao.saveLog(logToSave);
		}
	}
	
	private Target getTargetToSet(EventDTO eventDTO) {
		Location location = locationDao.getLocation(eventDTO.getTargetDTO().getLocationDTO());
		Target targetToSet = new Target(eventDTO.getTargetDTO());
		targetToSet.setLocation(location);
		Target targetToCheck = targetDao.getTarget(targetToSet);
		if (targetToCheck == null)
			targetToSet = targetDao.saveTarget(targetToSet);
		return targetToSet;
	}

	public void registerForAnEvent(ExternalUser userMakingRequest, String eventId) {
		Event event = eventDao.getEventById(eventId);
		if (event == null)
			throw new ItemNotFoundException("Event not found", "Event to register for cannot be found");
		User user = userDao.getUserByUUId(userMakingRequest.getId());
		List<User> users = event.getUsers();
		if (!users.contains(user)) {
			users.add(user);
			event.setUsers(users);
		}
	}
	
	public void unregisterFromAnEvent(ExternalUser userMakingRequest, String eventId) {
		Event event = eventDao.getEventById(eventId);
		if (event == null)
			throw new ItemNotFoundException("Event not found", "Event to register for cannot be found");
		User user = userDao.getUserByUUId(userMakingRequest.getId());
		List<User> users = event.getUsers();
		if (users.contains(user)) {
			users.remove(user);
			event.setUsers(users);
		}
	}
	
	public List<EventDTOLite> getAllEvents(ExternalUser userMakingRequest) {
		List<Event> allEvents = eventDao.getAllEvents();
		for (Event event : allEvents) {
			if (event.getTarget() != null) {
				if (!targetMatch(userMakingRequest, event.getTarget()))
					allEvents.remove(event);
			}
		}
		User user = userDao.getUserByUUId(userMakingRequest.getId());
		List<Event> eventsToAttend = user.getEvents();
		List<EventDTOLite> events = new ArrayList<EventDTOLite>();
		if (eventsToAttend != null) {
			for (Event matchingEvent : allEvents) {
				if (!eventsToAttend.contains(matchingEvent))
					events.add(new EventDTOLite(matchingEvent, "pending"));
				else
					events.add(new EventDTOLite(matchingEvent, "attending"));
			}
		} else {
			for (Event pendingEvent : allEvents) 
				events.add(new EventDTOLite(pendingEvent, "pending"));
		}
		return events;
	}
}