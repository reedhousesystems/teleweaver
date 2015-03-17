package com.reedhousesystems.services.core.health.dashboard.api;

import java.util.List;

import com.reedhousesystems.services.core.health.dashboard.dto.UUIDDTO;
import com.reedhousesystems.services.core.health.dashboard.model.Campaign;
import com.reedhousesystems.services.core.health.dashboard.model.Message;
import com.reedhousesystems.services.core.health.dashboard.model.Organisation;

public interface CampaignDao {
	
	public UUIDDTO saveCampaign(Campaign campaign) throws Exception;
	
	public void deleteCampaign(String campaignId) throws Exception;
	
	public Campaign getCampaignById(String campaignId) throws Exception;
	
	public List<Campaign> getAllCampaigns(Organisation organisation, String role) throws Exception;
	
	public boolean campaignTargetExists(String campaignId) throws Exception;
	
	public boolean messageExisits(String messageId) throws Exception;
	
	public void saveMessage(String campaignId, Message message) throws Exception;
	
	public void deleteMessage(String messageId) throws Exception;
	
	public Message getMessageById(String messageId) throws Exception;
	
	public List<Message> getMessagesByCampaignId(String campaignId) throws Exception;
	
	public List<Campaign> getCampaignsToReport(Organisation organisation, String role) throws Exception;
	
	public List<Campaign> getArchivedCampaigns(Organisation organisation, String role) throws Exception;
	
}