package com.reedhousesystems.services.core.health.messenger.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonBackReference;

import com.reedhousesystems.services.core.health.messenger.dto.LogDTO;

@Entity
@Table(name="LOG_TBL")
public class Log implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@NotNull
	@Column
	private String identifier;
	
	@NotNull
	@Column
	private String name;
	
	@NotNull
	@Column
	private String type;
	
	/*@NotNull
	@Column
	private int priority;*/
	
	@NotNull
	@Column(name="organisation_id")
	private String organisationId;
	
	@Column(name="start_date")
	private Date startDate;
	
	@Column(name="end_date")
	private Date endDate;
	
	@JsonBackReference("target")
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="target_id")
	private Target target;
	
	public Log() {
		
	}
	
	public Log(LogDTO logDTO) {
		this.name = logDTO.getName();
		this.type = logDTO.getType();
		this.setOrganisationId(logDTO.getOrganisationId());
	}
	
	public Log(String identifier, String name, String type, String organisationId, Date startDate, Date endDate, Target target) {
		this.identifier = identifier;
		this.name = name;
		this.type = type;
		this.organisationId = organisationId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.target = target;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/*public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}*/

	public String getOrganisationId() {
		return organisationId;
	}

	public void setOrganisationId(String organisationId) {
		this.organisationId = organisationId;
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

	public Target getTarget() {
		return target;
	}

	public void setTarget(Target target) {
		this.target = target;
	}
}