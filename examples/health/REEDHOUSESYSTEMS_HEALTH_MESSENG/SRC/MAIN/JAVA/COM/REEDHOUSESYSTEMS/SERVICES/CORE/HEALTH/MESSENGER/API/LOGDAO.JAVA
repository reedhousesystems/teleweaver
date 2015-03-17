package com.reedhousesystems.services.core.health.messenger.api;

import java.util.List;

import com.reedhousesystems.services.core.health.messenger.model.Log;

public interface LogDao {
	
	public void saveLog(Log log);
	
	public void deleteLog(Log log);
	
	public Log getLog(String logId);
	
	public List<Log> getLogsByOrganisationId(String organisationId);
	
	public List<Log> getAll();
		
}