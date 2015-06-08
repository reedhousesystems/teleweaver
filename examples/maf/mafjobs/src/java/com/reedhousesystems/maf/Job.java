package com.reedhousesystems.maf;

public class Job {
	String id, title, link, comments, date, description, creator;

	public Job() {
		this("", "", "", "", "", "", "");
	}

	public Job(String id, String title, String link, String date,String description) {
                this(id, title, link, "", date, description, "");
	}

	public Job(String id, String title, String link, String comments,
			String date, String description, String creator) {
		super();
		this.id = id;
		this.title = title;
		this.link = link;
		this.comments = comments;
		this.date = date;
		this.description = description;
		this.creator = creator;
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

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}
	
}
