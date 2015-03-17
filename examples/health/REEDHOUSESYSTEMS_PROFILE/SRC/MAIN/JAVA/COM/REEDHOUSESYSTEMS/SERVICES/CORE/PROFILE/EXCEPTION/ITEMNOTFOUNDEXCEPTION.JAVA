package com.reedhousesystems.services.core.profile.exception;

public class ItemNotFoundException extends BaseWebApplicationException {
	
	public ItemNotFoundException(String errorMessage, String developerMessage) {
        super(404, "40402", errorMessage, developerMessage);
    }
	
}