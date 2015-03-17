package com.reedhousesystems.services.core.health.messenger.api;

import java.util.List;

import com.reedhousesystems.services.core.health.messenger.model.Campaign;
import com.reedhousesystems.services.core.health.messenger.model.Message;

public interface CampaignDao {
	
	public void saveCampaign(Campaign campaign);
	
	public void deleteMessages(Message message);
	
	public Campaign getCampaignById(String campaignId);
	
	public List<Campaign> getRunningCampaigns();
	
	public List<Message> getMessagesByCampaignId(String campaignId);
	
}
