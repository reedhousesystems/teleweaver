package com.reedhousesystems.services.core.health.dashboard.api;

import com.reedhousesystems.services.core.health.dashboard.model.VerificationToken;

public interface VerificationTokenDao {
	
	public VerificationToken getVerificationTokenByUUID(String uuid);
	
	public VerificationToken getVerificationTokenByToken(String token);
	
	public <T> VerificationToken getVerificationToken(T field, T value);
}