package com.reedhousesystems.services.core.profile.api;

import com.reedhousesystems.services.core.profile.model.VerificationToken;

public interface VerificationTokenDao {
	
	public VerificationToken getVerificationTokenByUUID(String uuid);
	
	public VerificationToken getVerificationTokenByToken(String token);
	
	public <T> VerificationToken getVerificationToken(T field, T value);
}