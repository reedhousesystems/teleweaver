package com.reedhousesystems.services.core.health.dashboard.api;

import java.util.List;

import com.reedhousesystems.services.core.health.dashboard.dto.CampaignDTO;
import com.reedhousesystems.services.core.health.dashboard.dto.TargetDTO;
import com.reedhousesystems.services.core.health.dashboard.dto.UUIDDTO;
import com.reedhousesystems.services.core.health.dashboard.model.Campaign;
import com.reedhousesystems.services.core.health.dashboard.model.ExternalUser;
import com.reedhousesystems.services.core.health.dashboard.model.Message;
import com.reedhousesystems.services.core.health.dashboard.model.Target;
import com.reedhousesystems.services.core.health.dashboard.requests.CampaignRequest;
import com.reedhousesystems.services.core.health.dashboard.requests.MessageRequest;

public interface CampaignService {
	
	public UUIDDTO saveCampaign(CampaignRequest request) throws Exception;
	
	public void updateCampaign(String campaignId, CampaignRequest request) throws Exception;
	
	public void updateCampaign(String campaignId, String status) throws Exception;
	
	public void deleteCampaign(String campaignId) throws Exception;
	
	public CampaignRequest getCampaignById(String campaignId) throws Exception;
	
	public CampaignDTO getCampaignToPublish(String campaignId) throws Exception;
	
	public List<Campaign> getAllCampaigns(ExternalUser userMakingRequest) throws Exception;
	
	public void setCampaignTarget(String campaignId, TargetDTO targetDTO) throws Exception;
	
	public Target getCampaignTarget(String campaignId) throws Exception;
	
	public void saveMessage(String campaignId, MessageRequest request) throws Exception;
	
	public void updateMessage(String messageId, MessageRequest request) throws Exception;
	
	public void deleteMessage(String messageId) throws Exception;
	
	public Message getMessageById(String messageId) throws Exception;
	
	public List<Message> getMessagesByCampaignId(String campaignId) throws Exception;
	
	public CampaignDTO updateCampaignViews(String campaignId) throws Exception;
	
	public List<Campaign> getCampaignsToReport(ExternalUser userMakingRequest) throws Exception;
	
}