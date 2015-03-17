package com.reedhousesystems.services.core.health.dashboard.model;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @version 1.0
 * @author: C. Gremu
 * @since 28/07/2013
 */
@Entity
@Table(name="SESSION_TOKEN_TBL")
public class SessionToken implements Comparable<SessionToken>{

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
    @Column(length=36)
    private String token;

    @Column(name="time_created")
    private Date timeCreated;

    @Column(name="last_updated")
    private Date lastUpdated;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public SessionToken() {}

    public SessionToken(User user) {
        this.token = UUID.randomUUID().toString();
        this.user = user;
        this.timeCreated = new Date();
        this.lastUpdated = new Date();
    }

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

    public String getToken() {
        return token;
    }

    public User getUser() {
        return user;
    }

    public Date getTimeCreated() {
        return timeCreated;
    }

    public int compareTo(SessionToken userSession) {
        return this.lastUpdated.compareTo(userSession.getLastUpdated());
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
