package com.reedhousesystems.services.core.health.messenger.request;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.reedhousesystems.services.core.health.messenger.model.News;
import com.reedhousesystems.services.core.health.messenger.model.Target;

public class NewsRequest {
	
	private String id;
	
	@NotNull
	private String title;
	
	@NotNull
	private String content;
	
	@NotNull
	private String author;
	
	private String status;
	
	@Valid
	private Target target;
	
	public NewsRequest() {
		
	}
	
	public NewsRequest(News news) {
		setId(news.getUuid().toString());
		setTitle(news.getTitle());
		setContent(news.getContent());
		setAuthor(news.getAuthor());
		setStatus(news.getStatus());
		setTarget(news.getTarget());
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Target getTarget() {
		return target;
	}

	public void setTarget(Target target) {
		this.target = target;
	}
	
}