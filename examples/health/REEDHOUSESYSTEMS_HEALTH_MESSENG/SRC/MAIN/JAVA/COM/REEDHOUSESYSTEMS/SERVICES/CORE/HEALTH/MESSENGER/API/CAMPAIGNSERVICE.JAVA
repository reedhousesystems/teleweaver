package com.reedhousesystems.services.core.health.messenger.api;

import java.util.List;

import com.reedhousesystems.services.core.health.messenger.dto.CampaignDTO;
import com.reedhousesystems.services.core.health.messenger.model.ExternalUser;
import com.reedhousesystems.services.core.health.messenger.request.CampaignRequest;
import com.reedhousesystems.services.core.health.messenger.request.MessageRequest;

public interface CampaignService {

	public Integer saveOrUpdateCampaign(CampaignDTO campaign);

	public List<CampaignRequest> getRunningCampaigns(ExternalUser userMakingRequest);

	public List<MessageRequest> getMessagesByCampaignId(String campaignId, ExternalUser userMakingRequest);
	
}