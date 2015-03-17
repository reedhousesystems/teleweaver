package com.reedhousesystems.services.core.profile.request;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import com.reedhousesystems.services.core.profile.model.ExternalUser;

/**
 * @author: C. Gremu
 */
@XmlRootElement
public class CreateUserRequest {

    @NotNull
    @Valid
    private ExternalUser user;

    @NotNull
    @Valid
    private PasswordRequest password;


    public CreateUserRequest() {
    	
    }

	public ExternalUser getUser() {
		return user;
	}

	public void setUser(ExternalUser user) {
		this.user = user;
	}

    public PasswordRequest getPassword() {
        return password;
    }

    public void setPassword(PasswordRequest password) {
        this.password = password;
    }

}
