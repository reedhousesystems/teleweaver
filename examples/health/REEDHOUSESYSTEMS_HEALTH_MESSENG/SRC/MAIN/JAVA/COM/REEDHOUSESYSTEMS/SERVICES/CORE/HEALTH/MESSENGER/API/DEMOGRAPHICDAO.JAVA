package com.reedhousesystems.services.core.health.messenger.api;

import com.reedhousesystems.services.core.health.messenger.model.Target;

public interface DemographicDao {
	
	public Long getNumberOfMatchingUsers(Target target);
	
}