package com.reedhousesystems.services.core.health.messenger.api;

import com.reedhousesystems.services.core.health.messenger.model.Target;

public interface TargetDao {
	
	public Target saveTarget(Target target);
	
	public Target getTarget(String targetId);
	
	public Target getTarget(Target target);
	
}