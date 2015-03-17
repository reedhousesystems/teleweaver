package com.reedhousesystems.services.core.health.messenger.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonManagedReference;

import com.reedhousesystems.services.core.health.messenger.dto.TopicDTO;
import com.reedhousesystems.services.core.health.messenger.dto.TopicDetailsDTO;

@Entity
@Table(name="TOPIC_TBL")
public class Topic  extends BaseEntity {
	
	@Column
	private String title;
	
	@Column
	private String description;
	
	@Column(name="creation_date")
	private Date creationDate;
	
	@Column
	private String status;
	
	@Column
	private String language;
	
	@JsonManagedReference
	@OneToMany(mappedBy="topic", fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private List<TopicDetails> topicDetails = new ArrayList<TopicDetails>();

	public Topic() {}

	public Topic(UUID uuid) {
		super(uuid);
	}

	public Topic(TopicDTO topic) {
		this(UUID.fromString(topic.getId()));
		this.title = topic.getTitle();
		this.description = topic.getDescription();
		this.language = topic.getLanguage();
		this.status = topic.getStatus();
		for (TopicDetailsDTO detailsDTO : topic.getTopicDetailsDTOList()) {
			topicDetails.add(new TopicDetails(this, detailsDTO));
		}
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	public Date getCreationDate() {
		return creationDate;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return status;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
	
	public void setTopicDetails(List<TopicDetails> topicDetails) {
		this.topicDetails = topicDetails;
	}
	
	public List<TopicDetails> getTopicDetails() {
		return topicDetails;
	}
}