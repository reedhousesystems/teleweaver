package com.reedhousesystems.services.core.health.dashboard.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.reedhousesystems.services.core.health.dashboard.requests.FacilityRequest;

@Entity
@Table(name="FACILITY_TBL")
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@facilityId")
public class Facility  extends BaseEntity {
	
	@Column
	private String name;
	
	@Column
	private String type;
	
	@Column
	private String status;
	
	@Embedded
	private Address address;
	
	@ManyToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinTable(name="FACILITY_SERVICE_TBL",
    joinColumns={@JoinColumn(name="facility_id")},
    inverseJoinColumns={@JoinColumn(name="service_id")})
	private List<Services> services = new ArrayList<Services>();

	public Facility() {
		this(UUID.randomUUID());
	}

	public Facility(UUID uuid) {
		super(uuid);
	}
	
	public Facility(FacilityRequest request) {
		setName(request.getName());
		setType(request.getType());
		setAddress(request.getAddress());
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
	
	public void setServices(List<Services> services) {
		this.services = services;
	}
	
	public List<Services> getServices() {
		return services;
	}
	
	public Address getAddress() {
		return address;
	}
	
	public void setAddress(Address address) {
		this.address = address;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
}