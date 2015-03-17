package com.reedhousesystems.services.core.health.dashboard.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Address implements Serializable {
	@Column
	private String province;
	
	@Column
	private String district;
	
	@Column
	private String municipality;
	
	@Column
	private String village;
	
	public Address() {
		
	}
	
	public Address(Address address) {
		setProvince(address.getProvince());
		setDistrict(address.getDistrict());
		setMunicipality(address.getMunicipality());
		setVillage(address.getVillage());
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getMunicipality() {
		return municipality;
	}

	public void setMunicipality(String municipality) {
		this.municipality = municipality;
	}

	public String getVillage() {
		return village;
	}

	public void setVillage(String village) {
		this.village = village;
	}
}