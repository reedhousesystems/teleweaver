package com.reedhousesystems.services.core.health.dashboard.model;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonBackReference;

import com.reedhousesystems.services.core.health.dashboard.requests.NewsRequest;

@Entity
@Table(name="NEWS_TBL")
public class News  extends BaseEntity {
	
	@Column
	private String title;
	
	@Column
	private String content;
	
	@Column(name="creation_date")
	private Date creationDate;
	
	@Column
	private String author;
	
	@Column
	private String status;
	
	@JsonBackReference("campaign-organisation")
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="organisation_id")
	private Organisation organisation;

	@JsonBackReference("target")
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="target_id")
	private Target target;

	public News() {
		this(UUID.randomUUID());
	}

	public News(UUID uuid) {
		super(uuid);
	}
	
	public News(NewsRequest request) {
		this();
		setTitle(request.getTitle());
		setContent(request.getContent());
		setAuthor(request.getAuthor());
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	public Date getCreationDate() {
		return creationDate;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return status;
	}

	public Organisation getOrganisation() {
		return organisation;
	}

	public void setOrganisation(Organisation organisation) {
		this.organisation = organisation;
	}
	
	public Target getTarget() {
		return target;
	}
	
	public void setTarget(Target target) {
		this.target = target;
	}
	
}