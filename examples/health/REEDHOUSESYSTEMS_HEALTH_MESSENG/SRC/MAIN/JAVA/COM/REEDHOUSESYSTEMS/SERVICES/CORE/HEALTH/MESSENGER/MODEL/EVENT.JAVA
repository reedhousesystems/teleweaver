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
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.reedhousesystems.services.core.health.messenger.dto.EventDTO;
import com.reedhousesystems.services.core.health.messenger.request.EventRequest;

@Entity
@Table(name="EVENT_TBL")
public class Event extends BaseEntity {
	
	@Column
	private String name;
	
	@Column
	private String description;
	
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
	
	@ManyToMany(cascade=CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name="EVENT_USER_TBL",
    joinColumns={@JoinColumn(name="event_id")},
    inverseJoinColumns={@JoinColumn(name="user_id")})
	private List<User> users = new ArrayList<User>();
	
	public Event() {
		
	}
	
	public Event(UUID uuid){
		super(uuid);
	}
	
	
	public Event(EventRequest event) {
		this(UUID.fromString(event.getId()));
		this.name = event.getName();
		this.description = event.getDescription();
		this.startDate = event.getStartDate();
		this.endDate = event.getEndDate();
		this.status = event.getStatus();
		this.target = event.getTarget();
	}
	
	public Event(EventDTO eventDTO) {
		this(UUID.fromString(eventDTO.getId()));
		this.name = eventDTO.getName();
		this.description = eventDTO.getDescription();
		this.startDate = eventDTO.getStartDate();
		this.endDate = eventDTO.getEndDate();
		this.status = eventDTO.getStatus();
		this.target = new Target(eventDTO.getTargetDTO());
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
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

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
}