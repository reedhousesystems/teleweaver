package com.reedhousesystems.services.core.health.dashboard.api;

import com.reedhousesystems.services.core.health.dashboard.model.Target;

public interface TargetDao {
	
	public Target saveTarget(Target target);
	
	public Target getTarget(String targetId);
	
	public Target getTarget(Target target);
	
}