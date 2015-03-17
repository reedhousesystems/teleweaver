package com.reedhousesystems.services.core.health.dashboard.requests;

import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.reedhousesystems.services.core.health.dashboard.model.Campaign;
import com.reedhousesystems.services.core.health.dashboard.model.Target;

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
	
	private Date creationDate;
	
	@NotNull
	private Date startDate;
	
	@NotNull
	private Date endDate;
	
	private String status;
	
	private String userId;
	
	@Valid
	private Target target;
	
	public CampaignRequest() {
		
	}
	
	public CampaignRequest(Campaign campaign) {
		setId(campaign.getUuid().toString());
		setTitle(campaign.getTitle());
		setAim(campaign.getAim());
		setDescription(campaign.getDescription());
		setSlogan(campaign.getSlogan());
		setCreationDate(campaign.getCreationDate());
		setStartDate(campaign.getStartDate());
		setEndDate(campaign.getEndDate());
		setStatus(campaign.getStatus());
		setTarget(campaign.getTarget());
		this.userId = campaign.getOrganisation().getUuid().toString();
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

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Target getTarget() {
		return target;
	}

	public void setTarget(Target target) {
		this.target = target;
	}
	
}