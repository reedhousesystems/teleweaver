package com.reedhousesystems.services.core.health.messenger.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.reedhousesystems.services.core.health.messenger.dto.LocationDTO;
import com.reedhousesystems.services.core.health.messenger.request.LocationRequest;

@Entity
@Table(name="LOCATION_TBL")
public class Location  extends BaseEntity {
	
	@Column
	private String province;
	@Column
	private String district;
	@Column
	private String municipality;
	@Column
	private String village;

	public Location() {
		this(UUID.randomUUID());
	}

	public Location(UUID uuid) {
		super(uuid);
	}
	
	public Location(LocationRequest request) {
		this(UUID.fromString(request.getId()));
		setProvince(request.getProvince());
		setDistrict(request.getDistrict());
		setMunicipality(request.getMunicipality());
		setVillage(request.getVillage());
	}
	
	public Location(LocationDTO locationDTO) {
		this(UUID.fromString(locationDTO.getId()));
		this.province = locationDTO.getProvince();
		this.district = locationDTO.getDistrict();
		this.municipality = locationDTO.getMunicipality();
		this.village = locationDTO.getVillage();
	}
	
	public void setProvince(String province) {
		this.province = province;
	}
	
	public String getProvince() {
		return province;
	}
	
	public void setDistrict(String district) {
		this.district = district;
	}
	
	public String getDistrict() {
		return district;
	}
	
	public void setMunicipality(String municipality) {
		this.municipality = municipality;
	}
	
	public String getMunicipality() {
		return municipality;
	}
	
	public void setVillage(String village) {
		this.village = village;
	}
	
	public String getVillage() {
		return village;
	}

}