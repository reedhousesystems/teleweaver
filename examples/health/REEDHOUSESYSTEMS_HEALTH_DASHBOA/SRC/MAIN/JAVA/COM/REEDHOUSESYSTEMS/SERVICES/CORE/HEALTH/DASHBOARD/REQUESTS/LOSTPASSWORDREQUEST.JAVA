package com.reedhousesystems.services.core.health.dashboard.requests;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @version 1.0
 * @author: Iain Porter iain.porter@porterhead.com
 * @since 26/09/2012
 */
@XmlRootElement
public class LostPasswordRequest {

    @NotNull
    private String userName;

    public LostPasswordRequest() {}

    public LostPasswordRequest(final String userName) {
        this.setUserName(userName);
    }

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
