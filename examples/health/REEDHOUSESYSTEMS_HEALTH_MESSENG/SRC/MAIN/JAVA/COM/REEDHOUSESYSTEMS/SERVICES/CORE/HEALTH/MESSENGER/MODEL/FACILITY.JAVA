package com.reedhousesystems.services.core.health.messenger.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="FACILITY_TBL")
public class Facility extends BaseEntity {
	
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
	private List<Service> services = new ArrayList<Service>();
	
	public Facility() {
		
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
	
	public void setServices(List<Service> services) {
		this.services = services;
	}
	
	public List<Service> getServices() {
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