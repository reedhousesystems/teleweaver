package com.reedhousesystems.services.core.health.dashboard.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonManagedReference;

import com.reedhousesystems.services.core.health.dashboard.requests.CampaignRequest;

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
	
	@Column(name="number_of_views")
	private int numberOfViews;
	
	@JsonBackReference("campaign-organisation")
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="organisation_id")
	private Organisation organisation;

	@JsonBackReference("campaign-target")
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="target_id")
	private Target target;
	
	@JsonManagedReference("campaign-message")
	@OneToMany(mappedBy="campaign", fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private List<Message> messages = new ArrayList<Message>();

	public Campaign() {
		this(UUID.randomUUID());
	}

	public Campaign(UUID uuid) {
		super(uuid);
	}
	
	public Campaign(CampaignRequest request) {
	    this();
		setTitle(request.getTitle());
		setAim(request.getAim());
		setDescription(request.getDescription());
		setSlogan(request.getSlogan());
		setStartDate(request.getStartDate());
		setEndDate(request.getEndDate());
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

	public int getNumberOfViews() {
		return numberOfViews;
	}

	public void setNumberOfViews(int numberOfViews) {
		this.numberOfViews = numberOfViews;
	}

	public Organisation getOrganisation() {
		return organisation;
	}

	public void setOrganisation(Organisation organisation) {
		this.organisation = organisation;
	}
	
	public Target getTarget() {
		return target;
	}
	
	public void setTarget(Target target) {
		this.target = target;
	}

	public int hashCode() {
		return getUuid().hashCode();
	}
	
	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}
	
	public List<Message> getMessages() {
		return messages;
	}
	
}
