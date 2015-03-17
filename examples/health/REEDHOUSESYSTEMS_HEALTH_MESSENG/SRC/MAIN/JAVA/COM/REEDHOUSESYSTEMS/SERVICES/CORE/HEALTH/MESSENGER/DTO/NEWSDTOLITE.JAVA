package com.reedhousesystems.services.core.health.messenger.dto;

import javax.validation.constraints.NotNull;

import com.reedhousesystems.services.core.health.messenger.model.News;

public class NewsDTOLite {

	private String id;
	
	@NotNull
	private String title;
	
	@NotNull
	private String content;
	
	@NotNull
	private String author;
	
	public NewsDTOLite() {
		
	}
	
	public NewsDTOLite(News news) {
		this.id = news.getUuid().toString();
		this.title = news.getTitle();
		this.content = news.getContent();
		this.author = news.getAuthor();
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
}