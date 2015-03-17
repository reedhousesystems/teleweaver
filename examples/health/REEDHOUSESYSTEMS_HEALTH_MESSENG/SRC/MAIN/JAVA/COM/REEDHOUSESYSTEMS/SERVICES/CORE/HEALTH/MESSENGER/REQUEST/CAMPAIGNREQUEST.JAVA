package com.reedhousesystems.services.core.health.messenger.request;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.reedhousesystems.services.core.health.messenger.model.Campaign;

public class CampaignRequest {
	
	private String id;
	
	@NotNull
	private String title;
	
	@NotNull
	private String aim;
	
	@NotNull
	private String description;
	
	@NotNull
	private String slogan;
	
	@NotNull
	private Date startDate;
	
	@NotNull
	private Date endDate;
	
	@NotNull
	private String organisation;
	
	public CampaignRequest() {
		
	}
	
	public CampaignRequest(Campaign campaign) {
		this.id = campaign.getUuid().toString();
		this.title = campaign.getTitle();
		this.aim = campaign.getAim();
		this.description = campaign.getDescription();
		this.slogan = campaign.getSlogan();
		this.startDate = campaign.getStartDate();
		this.endDate = campaign.getEndDate();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAim() {
		return aim;
	}

	public void setAim(String aim) {
		this.aim = aim;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSlogan() {
		return slogan;
	}

	public void setSlogan(String slogan) {
		this.slogan = slogan;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getOrganisation() {
		return organisation;
	}

	public void setOrganisation(String organisation) {
		this.organisation = organisation;
	}
	
}