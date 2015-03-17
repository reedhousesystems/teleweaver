package com.reedhousesystems.services.core.health.messenger.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonBackReference;

import com.reedhousesystems.services.core.health.messenger.request.MessageRequest;

@Entity
@Table(name="MESSAGE_TBL")
public class Message extends BaseEntity {
	
	@Column
	private String item;
	
	@Column
	private String description;
	
	@JsonBackReference
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="campaign_id")
	private Campaign campaign;
	
	public Message() {
		
	}
	
	public Message(MessageRequest message) {
		super(UUID.fromString(message.getId()));
		this.item = message.getItem();
		this.description = message.getDescription();
	}
	
	public Message(Campaign campaign, MessageRequest message) {
		super(UUID.fromString(message.getId()));
		this.item = message.getItem();
		this.description = message.getDescription();
		this.campaign = campaign;
	}
	
	public void setItem(String item) {
		this.item = item;
	}
	
	public String getItem() {
		return item;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setCampaign(Campaign campaign) {
		this.campaign = campaign;
	}
	
	public Campaign getCampaign() {
		return campaign;
	}
}