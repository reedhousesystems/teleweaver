package com.reedhousesystems.services.core.health.messenger.api;

import com.reedhousesystems.services.core.health.messenger.model.VerificationToken;


public interface VerificationTokenDao {
	
	public VerificationToken getVerificationTokenByUUID(String uuid);
	
	public VerificationToken getVerificationTokenByToken(String token);
	
	public <T> VerificationToken getVerificationToken(T field, T value);
}