package com.reedhousesystems.services.core.health.messenger.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.reedhousesystems.services.core.health.messenger.api.CampaignDao;
import com.reedhousesystems.services.core.health.messenger.api.CampaignService;
import com.reedhousesystems.services.core.health.messenger.api.DemographicDao;
import com.reedhousesystems.services.core.health.messenger.api.LocationDao;
import com.reedhousesystems.services.core.health.messenger.api.LogDao;
import com.reedhousesystems.services.core.health.messenger.api.OrganisationDao;
import com.reedhousesystems.services.core.health.messenger.api.TargetDao;
import com.reedhousesystems.services.core.health.messenger.api.UserDao;
import com.reedhousesystems.services.core.health.messenger.dto.CampaignDTO;
import com.reedhousesystems.services.core.health.messenger.exception.ItemNotFoundException;
import com.reedhousesystems.services.core.health.messenger.model.Campaign;
import com.reedhousesystems.services.core.health.messenger.model.ExternalUser;
import com.reedhousesystems.services.core.health.messenger.model.Location;
import com.reedhousesystems.services.core.health.messenger.model.Log;
import com.reedhousesystems.services.core.health.messenger.model.Message;
import com.reedhousesystems.services.core.health.messenger.model.Organisation;
import com.reedhousesystems.services.core.health.messenger.model.Role;
import com.reedhousesystems.services.core.health.messenger.model.Target;
import com.reedhousesystems.services.core.health.messenger.model.User;
import com.reedhousesystems.services.core.health.messenger.request.CampaignRequest;
import com.reedhousesystems.services.core.health.messenger.request.MessageRequest;

@Service("campaignService")
@Transactional
public class CampaignServiceImpl extends BaseService implements CampaignService {
	
	private CampaignDao campaignDao;

	@Resource
	protected void setCampaignDao(CampaignDao campaignDao) {
		this.campaignDao = campaignDao;
	}
	@Resource
	private LogDao logDao;
	@Resource
	private OrganisationDao organisationDao;
	@Autowired
	private LocationDao locationDao;
	@Autowired
	private TargetDao targetDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private DemographicDao demographicDao;
    
	public Integer saveOrUpdateCampaign(CampaignDTO campaignDTO) {
		Campaign campaignToUpdate = campaignDao.getCampaignById(campaignDTO.getId());
		Integer numberOfMatchingUsers = 0;
		if (campaignToUpdate != null) {
			Organisation organisation = organisationDao.getOrganisation(campaignDTO.getOrganisation());
			if (organisation == null) {
				throw new ItemNotFoundException("Organisation not found", "Your organisation was not found. Please contact the system administrator to register your organisation");
			}
			campaignToUpdate.setOrganisation(organisation);
			campaignToUpdate.setTitle(campaignDTO.getTitle());
			campaignToUpdate.setAim(campaignDTO.getAim());
			campaignToUpdate.setDescription(campaignDTO.getDescription());
			campaignToUpdate.setSlogan(campaignDTO.getSlogan());
			campaignToUpdate.setStartDate(campaignDTO.getStartDate());
			campaignToUpdate.setEndDate(campaignDTO.getEndDate());
			campaignToUpdate.setStatus(campaignDTO.getStatus());
			for (Message messageToDelete : campaignToUpdate.getMessages()) {
				campaignDao.deleteMessages(messageToDelete);
			}
			List<Message> messages = new ArrayList<Message>();
			for (MessageRequest message : campaignDTO.getMessages()) {
				messages.add(new Message(campaignToUpdate, message));
			}
			campaignToUpdate.setMessages(messages);
			Log log = logDao.getLog(campaignDTO.getId());
			log.setName(campaignDTO.getSlogan());
			log.setStartDate(campaignDTO.getStartDate());
			log.setEndDate(campaignDTO.getEndDate());
			Target target = targetDao.getTarget(campaignDTO.getTargetDTO().getId());
			if (target != null) {
				campaignToUpdate.setTarget(target);
				log.setTarget(target);
			} else {
				Target targetToSet = getTargetToSet(campaignDTO);
				campaignToUpdate.setTarget(targetToSet);
				log.setTarget(targetToSet);
			}
			numberOfMatchingUsers = demographicDao.getNumberOfMatchingUsers(campaignToUpdate.getTarget()) != null ? demographicDao.getNumberOfMatchingUsers(campaignToUpdate.getTarget()).intValue() : null;
		} else {
			Log logToSave = null;
			Organisation organisation = organisationDao.getOrganisation(campaignDTO.getOrganisation());
			if (organisation == null) {
				throw new ItemNotFoundException("Organisation not found", "Your organisation was not found. Please contact the system administrator to register your organisation");
			}
			Campaign campaign = new Campaign(campaignDTO);
			Target target = targetDao.getTarget(campaignDTO.getTargetDTO().getId());
			if (target != null) {
				campaign.setTarget(target);
				logToSave = new Log(campaignDTO.getId(), campaignDTO.getTitle(), "campaigns", campaignDTO.getOrganisation(), campaignDTO.getStartDate(), campaignDTO.getEndDate(), target);
			} else {
				Target targetToSet = getTargetToSet(campaignDTO);
				campaign.setTarget(targetToSet);
				logToSave = new Log(campaignDTO.getId(), campaignDTO.getTitle(), "campaigns", campaignDTO.getOrganisation(), campaignDTO.getStartDate(), campaignDTO.getEndDate(), targetToSet);
			}
			campaign.setOrganisation(organisation);
			campaignDao.saveCampaign(campaign);
			logDao.saveLog(logToSave);
			numberOfMatchingUsers = demographicDao.getNumberOfMatchingUsers(campaign.getTarget()) != null ? demographicDao.getNumberOfMatchingUsers(campaign.getTarget()).intValue() : null;
		}
		return numberOfMatchingUsers;
	}
	
	private Target getTargetToSet(CampaignDTO campaignDTO) {
		Location location = locationDao.getLocation(campaignDTO.getTargetDTO().getLocationDTO());
		Target targetToSet = new Target(campaignDTO.getTargetDTO());
		targetToSet.setLocation(location);
		Target targetToCheck = targetDao.getTarget(targetToSet);
		if (targetToCheck == null)
			targetToSet = targetDao.saveTarget(targetToSet);
		return targetToSet;
	}
	
	public List<CampaignRequest> getRunningCampaigns(ExternalUser userMakingRequest) {
        Assert.notNull(userMakingRequest);
		List<Campaign> runningCampaigns = campaignDao.getRunningCampaigns();
		List<CampaignRequest> runningCampaignsList = new ArrayList<CampaignRequest>();
		if (userMakingRequest.getRole().equals(Role.authenticated.toString())) {
			for (Campaign campaign : runningCampaigns) {
				if (campaign.getTarget() != null) {
					if (targetMatch(userMakingRequest, campaign.getTarget())) {
						runningCampaignsList.add(new CampaignRequest(campaign));
					}
				} else {
					runningCampaignsList.add(new CampaignRequest(campaign));
				}
			}
		}
		return runningCampaignsList;
	}
	
	public List<MessageRequest> getMessagesByCampaignId(String campaignId, ExternalUser userMakingRequest) {
        Assert.notNull(campaignId);
		Campaign campaign = campaignDao.getCampaignById(campaignId);
		if (campaign == null)
			throw new ItemNotFoundException("Campaign not found", "The campaign might have been deleted so cannot be found");
		Hibernate.initialize(campaign.getMessages());
		List<Message> messageList = campaign.getMessages();
		List<MessageRequest> messages = new ArrayList<MessageRequest>();
		for (Message message : messageList) {
			messages.add(new MessageRequest(message));
		}
		User user = userDao.getUserByUUId(userMakingRequest.getId());
		List<User> users = campaign.getUsers();
		if (!users.contains(user)) {
			users.add(user);
			campaign.setUsers(users);
		}
		return messages;
	}
}