package com.reedhousesystems.services.core.health.messenger.dto;

public class LogTitleDTO {
	
	private String title;
	
	public LogTitleDTO() {
		
	}
	
	public LogTitleDTO(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}