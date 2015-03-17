package com.reedhousesystems.services.core.health.messenger.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reedhousesystems.services.core.health.messenger.api.LogDao;
import com.reedhousesystems.services.core.health.messenger.api.LogService;
import com.reedhousesystems.services.core.health.messenger.dto.LogDTO;
import com.reedhousesystems.services.core.health.messenger.dto.LogTitleDTO;
import com.reedhousesystems.services.core.health.messenger.exception.ItemNotFoundException;
import com.reedhousesystems.services.core.health.messenger.model.ExternalUser;
import com.reedhousesystems.services.core.health.messenger.model.Log;
import com.reedhousesystems.services.core.health.messenger.model.Target;

@Service("LogService")
@Transactional
public class LogServiceImpl implements LogService {
	
	private LogDao logDao;
	
	@Resource
	protected void setLogDao(LogDao logDao) {
		this.logDao = logDao;
	}

	public void saveLog(LogDTO logDTO) {
		logDao.saveLog(new Log(logDTO));
	}

	public void updateLog(String logId, LogDTO logDTO) {
		Log logToUpdate = logDao.getLog(logId);
		if (logToUpdate == null)
			throw new ItemNotFoundException("Log not found", "The log requested cannot be found");
		logToUpdate.setName(logDTO.getName());
		logToUpdate.setType(logDTO.getType());
		logToUpdate.setOrganisationId(logDTO.getOrganisationId());
		logToUpdate.setStartDate(logDTO.getStartDate());
		logToUpdate.setEndDate(logDTO.getEndDate());
	}

	public void deleteLog(String logId) {
		Log logToDelete = logDao.getLog(logId);
		if (logToDelete == null)
			throw new ItemNotFoundException("Log not found", "The log requested cannot be found");
		logDao.deleteLog(logToDelete);
	}

	public LogDTO getLog(String logId) {
		Log log = logDao.getLog(logId);
		return new LogDTO(log);
	}

	public List<LogDTO> getLogsByOrganisationId(String organisationId) {
		List<Log> logs = logDao.getLogsByOrganisationId(organisationId);
		List<LogDTO> logDTOs = new ArrayList<LogDTO>();
		for (Log log : logs) {
			LogDTO logDTO = new LogDTO(log);
			logDTOs.add(logDTO);
		}
		return logDTOs;
	}

	public LogDTO getTargetedLog(ExternalUser userMakingRequest) {
		List<Log> logs = logDao.getAll();
		LogDTO logDTO = new LogDTO();
		for (Log log : logs) {
			if (log.getTarget() != null) {
				if (targetMatch(userMakingRequest, log.getTarget())) {
//					logDTOs.add(new LogDTO(log));
					logDTO = new LogDTO(log);
				}
			} else {
//				logDTOs.add(new LogDTO(log));
				logDTO = new LogDTO(log);
			}
		}
		return logDTO;
//		return logDTOs.get(0);
	}
	
	private boolean targetMatch(ExternalUser userMakingRequest, Target target) {
		boolean targetMatch = true;
		if (targetMatch == true && target.getLanguage() != null && target.getLanguage() != "") {
			if (!userMakingRequest.getDemographic().getLanguage().equals(target.getLanguage())) targetMatch = false;
		}
		if (targetMatch == true && target.getLocation().getProvince() != null && target.getLocation().getProvince() != "") {
			if (!userMakingRequest.getDemographic().getLocation().getProvince().equals(target.getLocation().getProvince())) targetMatch = false;
		}
		if (targetMatch == true && target.getLocation().getDistrict() != null && target.getLocation().getDistrict() != "") {
			if (!userMakingRequest.getDemographic().getLocation().getDistrict().equals(target.getLocation().getDistrict())) targetMatch = false;
		}
		if (targetMatch == true && target.getLocation().getMunicipality() != null && target.getLocation().getMunicipality() != "") {
			if (!userMakingRequest.getDemographic().getLocation().getMunicipality().equals(target.getLocation().getMunicipality())) targetMatch = false;
		}
		if (targetMatch == true && target.getLocation().getVillage() != null && target.getLocation().getVillage() != "") {
			if (!userMakingRequest.getDemographic().getLocation().getVillage().equals(target.getLocation().getVillage())) targetMatch = false;
		}
		if (targetMatch == true && target.getDateOfBirthFrom() != null) {
			if (userMakingRequest.getDemographic().getDateOfBirth().compareTo(target.getDateOfBirthFrom()) <= 0) targetMatch = false;
		}
		if (targetMatch == true && target.getDateOfBirthTo() != null) {
			if (userMakingRequest.getDemographic().getDateOfBirth().compareTo(target.getDateOfBirthTo()) >= 0) targetMatch = false;
		}
		if (targetMatch == true && target.getMaritalStatus() != null && target.getMaritalStatus() != "") {
			if (!userMakingRequest.getDemographic().getMaritalStatus().equals(target.getMaritalStatus())) targetMatch = false;
		}
		if (targetMatch == true && target.getOccupation() != null && target.getOccupation() != "") {
			if (!userMakingRequest.getDemographic().getOccupation().equals(target.getOccupation())) targetMatch = false;
		}
		if (targetMatch == true && target.getGender() != null && target.getGender() != "") {
			if (!userMakingRequest.getDemographic().getGender().equals(target.getGender())) targetMatch = false;
		}
		if (targetMatch == true && target.getSexOrientation() != null && target.getSexOrientation() != "") {
			if (!userMakingRequest.getDemographic().getSexOrientation().equals(target.getSexOrientation())) targetMatch = false;
		}
		return targetMatch;		
	}

	public List<LogTitleDTO> getLogTitles(ExternalUser userMakingRequest) {
		List<Log> logs = logDao.getAll();
		List<LogTitleDTO> logTitles = new ArrayList<LogTitleDTO>();
		for (Log log : logs) {
			if (log.getTarget() != null) {
				if (targetMatch(userMakingRequest, log.getTarget())) {
					logTitles.add(new LogTitleDTO(log.getName()));
				}
			} else {
				logTitles.add(new LogTitleDTO(log.getName()));
			}
		}
		return logTitles;
	}
}