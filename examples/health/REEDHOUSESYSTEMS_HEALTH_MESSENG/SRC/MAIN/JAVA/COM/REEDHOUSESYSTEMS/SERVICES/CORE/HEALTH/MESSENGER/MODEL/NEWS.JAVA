package com.reedhousesystems.services.core.health.messenger.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.reedhousesystems.services.core.health.messenger.dto.NewsDTO;
import com.reedhousesystems.services.core.health.messenger.request.NewsRequest;

@Entity
@Table(name="NEWS_TBL")
public class News extends BaseEntity {
	
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

	@JsonBackReference("target")
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="target_id")
	private Target target;
	
	@JsonBackReference("organisation")
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="organisation_id")
	private Organisation organisation;
	
	@ManyToMany(cascade=CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name="NEWS_USER_TBL",
    joinColumns={@JoinColumn(name="news_id")},
    inverseJoinColumns={@JoinColumn(name="user_id")})
	private List<User> users = new ArrayList<User>();
	
	public News() {
		
	}
	
	public News(UUID uuid){
		super(uuid);
	}
	
	public News(NewsRequest news) {
		this(UUID.fromString(news.getId()));
		this.title = news.getTitle();
		this.content = news.getContent();
		this.author = news.getAuthor();
		this.status = news.getStatus();
		this.target = news.getTarget();
	}
	
	public News(NewsDTO newsDTO) {
		this(UUID.fromString(newsDTO.getId()));
		this.title = newsDTO.getTitle();
		this.content = newsDTO.getContent();
		this.author = newsDTO.getAuthor();
		this.status = newsDTO.getStatus();
		this.target = new Target(newsDTO.getTargetDTO());
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
	
	public Target getTarget() {
		return target;
	}
	
	public void setTarget(Target target) {
		this.target = target;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public Organisation getOrganisation() {
		return organisation;
	}

	public void setOrganisation(Organisation organisation) {
		this.organisation = organisation;
	}
}