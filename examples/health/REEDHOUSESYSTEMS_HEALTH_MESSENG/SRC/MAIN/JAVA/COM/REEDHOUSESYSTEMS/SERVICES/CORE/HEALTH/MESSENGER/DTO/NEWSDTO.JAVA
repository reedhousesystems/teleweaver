package com.reedhousesystems.services.core.health.messenger.dto;

import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.reedhousesystems.services.core.health.messenger.model.News;

public class NewsDTO {
	
	@NotNull
	private String id;
	
	@NotNull
	private String title;
	
	@NotNull
	private String content;
	
	private Date creationDate;
	
	@NotNull
	private String author;
	
	private String status;
	
	@NotNull
	private String organisation;
	
	@Valid
	private TargetDTO targetDTO;
	
	public NewsDTO() {
		
	}
	
	public NewsDTO(News news) {
		this.id = news.getUuid().toString();
		this.title = news.getTitle();
		this.content = news.getContent();
		this.author = news.getAuthor();
		this.creationDate = news.getCreationDate();
		this.status = news.getStatus();
		this.targetDTO = new TargetDTO(news.getTarget());
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOrganisation() {
		return organisation;
	}

	public void setOrganisation(String organisation) {
		this.organisation = organisation;
	}

	public TargetDTO getTargetDTO() {
		return targetDTO;
	}

	public void setTargetDTO(TargetDTO targetDTO) {
		this.targetDTO = targetDTO;
	}
}