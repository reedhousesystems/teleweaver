package com.reedhousesystems.services.core.health.messenger.model;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

/**
 * @author C. Gremu
 * 
 * This class contains information generated when a user clicks on a billable activity
 * 
 */

public class MessageLog extends BaseEntity implements Serializable {
	
	@NotNull
	private String messageId;
	
	@NotNull
	private String message;
	
	@NotNull
	private Date clickTime;
	
	public MessageLog() {}
	
	public MessageLog(String messageId, String message, Date clickTime) {
		this.messageId = messageId;
		this.message = message;
		this.clickTime = clickTime;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getClickTime() {
		return clickTime;
	}

	public void setClickTime(Date clickTime) {
		this.clickTime = clickTime;
	}
	
}