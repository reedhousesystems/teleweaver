package com.reedhousesystems.services.core.health.dashboard.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="TELEWEAVER_TBL")
public class Teleweaver extends BaseEntity {
	
	@Column
	private String name;
	
	@Column
	private String URL;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getURL() {
		return URL;
	}

	public void setURL(String uRL) {
		URL = uRL;
	}
}