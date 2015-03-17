package com.reedhousesystems.services.core.profile.request;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;


/**
 *
 * @author: Iain Porter
 */
@XmlRootElement
public class LoginRequest {

    @NotNull
    private String username;

    @Length(min=8)
    @NotNull
    private String password;
    
    @NotNull
    private int status;

    public LoginRequest(){}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
    
}
