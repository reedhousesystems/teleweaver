package com.reedhousesystems.services.core.health.dashboard.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reedhousesystems.services.core.health.dashboard.api.CampaignDao;
import com.reedhousesystems.services.core.health.dashboard.api.CampaignService;
import com.reedhousesystems.services.core.health.dashboard.api.LocationDao;
import com.reedhousesystems.services.core.health.dashboard.api.OrganisationDao;
import com.reedhousesystems.services.core.health.dashboard.api.TargetDao;
import com.reedhousesystems.services.core.health.dashboard.api.UserDao;
import com.reedhousesystems.services.core.health.dashboard.dto.CampaignDTO;
import com.reedhousesystems.services.core.health.dashboard.dto.TargetDTO;
import com.reedhousesystems.services.core.health.dashboard.dto.UUIDDTO;
import com.reedhousesystems.services.core.health.dashboard.exception.ItemNotFoundException;
import com.reedhousesystems.services.core.health.dashboard.model.Campaign;
import com.reedhousesystems.services.core.health.dashboard.model.ExternalUser;
import com.reedhousesystems.services.core.health.dashboard.model.Location;
import com.reedhousesystems.services.core.health.dashboard.model.Message;
import com.reedhousesystems.services.core.health.dashboard.model.Organisation;
import com.reedhousesystems.services.core.health.dashboard.model.Target;
import com.reedhousesystems.services.core.health.dashboard.requests.CampaignRequest;
import com.reedhousesystems.services.core.health.dashboard.requests.MessageRequest;

@Service("campaignService")
@Transactional
public class CampaignServiceImpl implements CampaignService {
	
	@Resource
	private CampaignDao campaignDao;

    @Resource
    private UserDao userDao;
    
    @Resource
    private OrganisationDao organisationDao;
    
    @Resource
	private TargetDao targetDao;
    
    @Resource
    private LocationDao locationDao;
	
	public UUIDDTO saveCampaign(CampaignRequest request) throws Exception {
		UUIDDTO uuidDTO = null;
		try {
			Campaign newCampaign = createNewCampaign(request);
			uuidDTO = campaignDao.saveCampaign(newCampaign);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return uuidDTO;
	}

	public void updateCampaign(String campaignId, CampaignRequest request) throws Exception {
		try {
			Campaign campaignToUpdate = campaignDao.getCampaignById(campaignId);
			if (request.getAim() != null) campaignToUpdate.setAim(request.getAim());
			if (request.getDescription() != null) campaignToUpdate.setDescription(request.getDescription());
			if (request.getEndDate() != null) campaignToUpdate.setEndDate(request.getEndDate());
			if (request.getTitle() != null) campaignToUpdate.setTitle(request.getTitle());
			if (request.getSlogan() != null) campaignToUpdate.setSlogan(request.getSlogan());
			if (request.getStartDate() != null) campaignToUpdate.setStartDate(request.getStartDate());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void updateCampaign(String campaignId, String status) throws Exception {
		try {
			Campaign campaignToUpdate = campaignDao.getCampaignById(campaignId);
			campaignToUpdate.setStatus(status);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void deleteCampaign(String campaignId) throws Exception {
		try {
			campaignDao.deleteCampaign(campaignId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public CampaignRequest getCampaignById(String campaignId) throws Exception {
		Campaign campaign = null;
		try {
			campaign = campaignDao.getCampaignById(campaignId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new CampaignRequest(campaign);
	}

	public CampaignDTO getCampaignToPublish(String campaignId) throws Exception {
		Campaign campaign = null;
		try {
			campaign = campaignDao.getCampaignById(campaignId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (campaign == null)
			throw new ItemNotFoundException("Campaign Not Found", "Campaign requested cannot be found either because it has been deleted");
		if (campaign.getMessages() == null)
			throw new ItemNotFoundException("No Messages", "Campaign does not have messages. Please add messages before publishing");
		return new CampaignDTO(campaign);
	}

	public List<Campaign> getAllCampaigns(ExternalUser userMakingRequest) throws Exception {
		Organisation organisation = organisationDao.getOrganisationById(userMakingRequest.getOrganisation());
		String role = userMakingRequest.getRole();
		List<Campaign> campaignList = new ArrayList<Campaign>();
		try {
			campaignList = campaignDao.getAllCampaigns(organisation, role);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return campaignList;
	}

	public void setCampaignTarget(String campaignId, TargetDTO targetDTO) throws Exception {		
		try {
			Campaign campaign = campaignDao.getCampaignById(campaignId);
			if (campaign == null)
				throw new ItemNotFoundException("Campaign not found", "Campaign to set target cannot be found");
			Location location = locationDao.getLocation(targetDTO.getLocationDTO());
			if (location == null)
				throw new ItemNotFoundException("Location not found", "Location specified cannot be found");
			Target targetToCheck = new Target(targetDTO);
			targetToCheck.setLocation(location);
			Target target = targetDao.getTarget(targetToCheck);
			if (target == null)
				target = targetDao.saveTarget(targetToCheck);
			campaign.setTarget(target);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public Target getCampaignTarget(String campaignId) throws Exception {
		Target target = null;
		try {
			Campaign campaign = campaignDao.getCampaignById(campaignId);
			target = campaign.getTarget();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return target;
	}

	public void saveMessage(String campaignId, MessageRequest request) throws Exception {
		try {
			Message messageToSave = new Message(request);
			campaignDao.saveMessage(campaignId, messageToSave);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void updateMessage(String messageId, MessageRequest request) throws Exception {
		try {
			Message existingMessage = campaignDao.getMessageById(messageId);
			existingMessage.setItem(request.getItem());
			existingMessage.setDescription(request.getDescription());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void deleteMessage(String messageId) throws Exception {
		try {
			campaignDao.deleteMessage(messageId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public Message getMessageById(String messageId) throws Exception {
		Message message = new Message();
		try {
			message = campaignDao.getMessageById(messageId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return message;
	}

	public List<Message> getMessagesByCampaignId(String campaignId) throws Exception {
		List<Message> messageList = new ArrayList<Message>();
		try {
			messageList = campaignDao.getMessagesByCampaignId(campaignId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return messageList;
	}
	
	private Campaign createNewCampaign(CampaignRequest request) {
		Campaign newCampaign = new Campaign(request);
		Organisation organisation = userDao.getOrganisationByUserId(request.getUserId());
		newCampaign.setOrganisation(organisation);
		Date creationDate = new Date();
		newCampaign.setCreationDate(creationDate);
		newCampaign.setStatus("Active");
		return newCampaign;		
	}

	public CampaignDTO updateCampaignViews(String campaignId)  throws Exception {
		Campaign campaign = null;
		try {
			int numberOfViews = 0;
			campaign = campaignDao.getCampaignById(campaignId);
			numberOfViews = campaign.getNumberOfViews() + 1;
			campaign.setNumberOfViews(numberOfViews);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new CampaignDTO(campaign);
	}

	public List<Campaign> getCampaignsToReport(ExternalUser userMakingRequest) throws Exception {
		Organisation organisation = organisationDao.getOrganisationById(userMakingRequest.getOrganisation());
		String role = userMakingRequest.getRole();
		List<Campaign> campaignList = new ArrayList<Campaign>();
		try {
			campaignList = campaignDao.getCampaignsToReport(organisation, role);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return campaignList;
	}	
}