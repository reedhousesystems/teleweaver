package com.reedhousesystems.services.core.profile.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Address {
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
	
	public Address(String province, String district, String municipality, String village) {
		setProvince(province);
		setDistrict(district);
		setMunicipality(municipality);
		setVillage(village);
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