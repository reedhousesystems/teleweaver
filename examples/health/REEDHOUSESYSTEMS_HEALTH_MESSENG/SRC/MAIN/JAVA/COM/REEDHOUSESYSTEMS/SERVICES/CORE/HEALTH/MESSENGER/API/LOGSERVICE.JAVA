package com.reedhousesystems.services.core.health.messenger.api;

import java.util.List;

import com.reedhousesystems.services.core.health.messenger.dto.LogDTO;
import com.reedhousesystems.services.core.health.messenger.dto.LogTitleDTO;
import com.reedhousesystems.services.core.health.messenger.model.ExternalUser;

public interface LogService {
	
	public void saveLog(LogDTO logDTO);
	
	public void updateLog(String logId, LogDTO logDTO);
	
	public void deleteLog(String logId);
	
	public LogDTO getLog(String logId);
	
	public List<LogDTO> getLogsByOrganisationId(String organisationId);
	
	public LogDTO getTargetedLog(ExternalUser userMakingRequest);
	
	public List<LogTitleDTO> getLogTitles(ExternalUser userMakingRequest);
	
}