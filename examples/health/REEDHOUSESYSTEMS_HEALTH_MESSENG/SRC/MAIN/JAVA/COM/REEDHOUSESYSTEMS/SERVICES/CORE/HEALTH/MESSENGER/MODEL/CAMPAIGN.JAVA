package com.reedhousesystems.services.core.health.messenger.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonManagedReference;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.reedhousesystems.services.core.health.messenger.dto.CampaignDTO;
import com.reedhousesystems.services.core.health.messenger.request.MessageRequest;

@Entity
@Table(name="CAMPAIGN_TBL")
public class Campaign extends BaseEntity {
	
	@Column
	private String title;
	
	@Column
	private String aim;
	
	@Column
	private String description;
	
	@Column
	private String slogan;
	
	@Column(name="creation_date")
	private Date creationDate;
	
	@Column(name="start_date")
	private Date startDate;
	
	@Column(name="end_date")
	private Date endDate;
	
	@Column
	private String status;

	@JsonBackReference("target")
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="target_id")
	private Target target;
	
	@JsonBackReference("organisation")
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="organisation_id")
	private Organisation organisation;
	
	@JsonManagedReference("message")
	@OneToMany(mappedBy="campaign", fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private List<Message> messages = new ArrayList<Message>();
	
	@ManyToMany(cascade=CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name="CAMPAIGN_USER_TBL",
    joinColumns={@JoinColumn(name="campaign_id")},
    inverseJoinColumns={@JoinColumn(name="user_id")})
	private List<User> users = new ArrayList<User>();

	public Campaign() {
		this(UUID.randomUUID());
	}

	public Campaign(UUID uuid) {
		super(uuid);
	}
	
	public Campaign(CampaignDTO campaignDTO) {
		this(UUID.fromString(campaignDTO.getId()));
		this.title = campaignDTO.getTitle();
		this.aim = campaignDTO.getAim();
		this.description = campaignDTO.getDescription();
		this.slogan = campaignDTO.getSlogan();
		this.creationDate = campaignDTO.getCreationDate();
		this.startDate = campaignDTO.getStartDate();
		this.endDate = campaignDTO.getEndDate();
		this.status = campaignDTO.getStatus();
		this.target = new Target(campaignDTO.getTargetDTO());
		for (MessageRequest message : campaignDTO.getMessages()) {
			messages.add(new Message(this, message));
		}
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setAim(String aim) {
		this.aim = aim;
	}
	
	public String getAim() {
		return aim;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setSlogan(String slogan) {
		this.slogan = slogan;
	}
	
	public String getSlogan() {
		return slogan;
	}
	
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	public Date getCreationDate() {
		return creationDate;
	}
	
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	public Date getStartDate() {
		return startDate;
	}
	
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public Date getEndDate() {
		return endDate;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return status;
	}
	
	public Target getTarget() {
		return target;
	}
	
	public void setTarget(Target target) {
		this.target = target;
	}

	public Organisation getOrganisation() {
		return organisation;
	}

	public void setOrganisation(Organisation organisation) {
		this.organisation = organisation;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
	
}
