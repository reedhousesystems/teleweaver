package com.reedhousesystems.services.core.profile.model;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author: C. D. Gremu
 */
@Entity
@Table(name="SESSION_TOKEN_TBL")
public class SessionToken extends BaseEntity implements Comparable<SessionToken>{
	
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
