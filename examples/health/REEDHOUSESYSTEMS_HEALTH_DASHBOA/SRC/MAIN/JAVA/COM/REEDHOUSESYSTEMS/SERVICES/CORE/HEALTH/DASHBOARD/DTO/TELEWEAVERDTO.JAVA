package com.reedhousesystems.services.core.health.dashboard.dto;

import javax.validation.constraints.NotNull;

public class TeleweaverDTO {

	@NotNull
	private String id;

	@NotNull
	private String name;

	@NotNull
	private String URL;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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