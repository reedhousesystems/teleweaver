package com.reedhousesystems.services.core.health.messenger.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.reedhousesystems.services.core.health.messenger.model.Campaign;
import com.reedhousesystems.services.core.health.messenger.model.Message;
import com.reedhousesystems.services.core.health.messenger.request.MessageRequest;

public class CampaignDTO {
	
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
	
	@NotNull
	private String language;
	
	@NotNull
	private String organisation;
	
	@Valid
	private TargetDTO targetDTO;
	
	private List<MessageRequest> messages = new ArrayList<MessageRequest>();
	
	public CampaignDTO() {
		
	}
	
	public CampaignDTO(Campaign campaign) {
		this.id = campaign.getUuid().toString();
		this.title = campaign.getTitle();
		this.aim = campaign.getAim();
		this.description = campaign.getDescription();
		this.slogan = campaign.getSlogan();
		this.creationDate = campaign.getCreationDate();
		this.startDate = campaign.getStartDate();
		this.endDate = campaign.getEndDate();
		this.status = campaign.getStatus();
		this.targetDTO = new TargetDTO(campaign.getTarget());
		for (Message message : campaign.getMessages()) {
			messages.add(new MessageRequest(message));
		}
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

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getOrganisation() {
		return organisation;
	}

	public void setOrganisation(String organisation) {
		this.organisation = organisation;
	}

	public TargetDTO getTargetDTO() {
		return targetDTO;
	}

	public void setTargetDTO(TargetDTO targetDTO) {
		this.targetDTO = targetDTO;
	}

	public List<MessageRequest> getMessages() {
		return messages;
	}

	public void setMessages(List<MessageRequest> messages) {
		this.messages = messages;
	}
	
}