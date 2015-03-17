package com.reedhousesystems.services.core.health.dashboard.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.reedhousesystems.services.core.health.dashboard.model.Campaign;

public class CampaignDTOLite {
	
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
	
	private String status;
	
	public CampaignDTOLite() {
		
	}
	
	public CampaignDTOLite(Campaign campaign) {
		this.setId(campaign.getUuid().toString());
		this.setTitle(campaign.getTitle());
		this.setAim(campaign.getAim());
		this.setDescription(campaign.getDescription());
		this.setSlogan(campaign.getSlogan());
		this.setStartDate(campaign.getStartDate());
		this.setEndDate(campaign.getEndDate());
		this.setStatus(campaign.getStatus());
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}