package com.reedhousesystems.services.core.health.messenger.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author: Chiku Gremu
 */
@XmlRootElement
public class AuthenticatedUserToken {

    private String userId;
    private String firstName;
    private String lastName;
    private String token;
    private boolean profileComplete;

    public AuthenticatedUserToken(){}

    public AuthenticatedUserToken(String userId, String firstName, String lastName, String sessionToken, boolean profileComplete) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.token = sessionToken;
        this.profileComplete = profileComplete;
    }

    public String getUserId() {
        return userId;
    }

    public String getToken() {
        return token;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setToken(String token) {
        this.token = token;
    }

	public boolean isProfileComplete() {
		return profileComplete;
	}

	public void setProfileComplete(boolean profileComplete) {
		this.profileComplete = profileComplete;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}
