package com.reedhousesystems.services.core.health.dashboard.model;

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

    public AuthenticatedUserToken(){}

    public AuthenticatedUserToken(String userId, String sessionToken, String firstName, String lastName) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.token = sessionToken;
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
