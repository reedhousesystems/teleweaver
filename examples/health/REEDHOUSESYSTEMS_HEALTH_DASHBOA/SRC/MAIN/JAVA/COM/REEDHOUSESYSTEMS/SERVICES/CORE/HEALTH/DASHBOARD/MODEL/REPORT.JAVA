package com.reedhousesystems.services.core.health.dashboard.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonBackReference;

import com.reedhousesystems.services.core.health.dashboard.dto.ReportDTO;

@Entity
@Table(name="REPORT_TBL")
public class Report extends BaseEntity {
	
	@NotNull
	@Column	
	private String title;
	
	@NotNull
	@Column	
	private String type;
	
	@NotNull
	@Column(name="number_of_views")
	private int  numberOfViews;
	
	@JsonBackReference("campaign-organisation")
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="organisation_id")
	private Organisation organisation;
	
	public Report() {
		
	}
	
	public Report(UUID uuid) {
		super(uuid);
	}
	
	public Report(ReportDTO reportDTO) {
		this(UUID.fromString(reportDTO.getId()));
		this.title = reportDTO.getTitle();
		this.type = reportDTO.getType();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getNumberOfViews() {
		return numberOfViews;
	}

	public void setNumberOfViews(int numberOfViews) {
		this.numberOfViews = numberOfViews;
	}

	public Organisation getOrganisation() {
		return organisation;
	}

	public void setOrganisation(Organisation organisation) {
		this.organisation = organisation;
	}
}