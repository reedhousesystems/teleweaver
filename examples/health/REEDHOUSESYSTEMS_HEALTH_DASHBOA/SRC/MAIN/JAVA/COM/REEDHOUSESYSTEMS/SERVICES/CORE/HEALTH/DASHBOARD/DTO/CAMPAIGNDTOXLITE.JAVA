package com.reedhousesystems.services.core.health.dashboard.dto;

import javax.validation.constraints.NotNull;

import com.reedhousesystems.services.core.health.dashboard.model.Campaign;

public class CampaignDTOXLite {
	
	@NotNull
	private String title;
	
	@NotNull
	private int numberOfViews;
	
	public CampaignDTOXLite() {
		
	}
	
	public CampaignDTOXLite(Campaign campaign) {
		this.title = campaign.getTitle();
		this.numberOfViews = campaign.getNumberOfViews();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getNumberOfViews() {
		return numberOfViews;
	}

	public void setNumberOfViews(int numberOfViews) {
		this.numberOfViews = numberOfViews;
	}
}