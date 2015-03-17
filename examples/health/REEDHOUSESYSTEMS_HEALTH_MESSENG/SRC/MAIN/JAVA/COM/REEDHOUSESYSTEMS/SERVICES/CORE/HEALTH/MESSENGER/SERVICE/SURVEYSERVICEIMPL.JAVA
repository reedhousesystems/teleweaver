package com.reedhousesystems.services.core.health.messenger.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.reedhousesystems.services.core.health.messenger.api.LocationDao;
import com.reedhousesystems.services.core.health.messenger.api.LogDao;
import com.reedhousesystems.services.core.health.messenger.api.OrganisationDao;
import com.reedhousesystems.services.core.health.messenger.api.SurveyDao;
import com.reedhousesystems.services.core.health.messenger.api.SurveyService;
import com.reedhousesystems.services.core.health.messenger.api.TargetDao;
import com.reedhousesystems.services.core.health.messenger.dto.QuestionDTO;
import com.reedhousesystems.services.core.health.messenger.dto.SurveyDTO;
import com.reedhousesystems.services.core.health.messenger.dto.SurveyDTOLite;
import com.reedhousesystems.services.core.health.messenger.exception.AuthorizationException;
import com.reedhousesystems.services.core.health.messenger.exception.ItemNotFoundException;
import com.reedhousesystems.services.core.health.messenger.model.Answer;
import com.reedhousesystems.services.core.health.messenger.model.ExternalUser;
import com.reedhousesystems.services.core.health.messenger.model.Location;
import com.reedhousesystems.services.core.health.messenger.model.Log;
import com.reedhousesystems.services.core.health.messenger.model.Organisation;
import com.reedhousesystems.services.core.health.messenger.model.Question;
import com.reedhousesystems.services.core.health.messenger.model.Role;
import com.reedhousesystems.services.core.health.messenger.model.Survey;
import com.reedhousesystems.services.core.health.messenger.model.Target;
import com.reedhousesystems.services.core.health.messenger.request.SurveyRequest;

@Service("surveyService")
@Transactional
public class SurveyServiceImpl extends BaseService implements SurveyService {

	@Autowired
	private SurveyDao surveyDao;
	
	@Resource
	private OrganisationDao organisationDao;
	
	private LogDao logDao;
	
	@Resource
	protected void setLogDao(LogDao logDao) {
		this.logDao = logDao;
	}

	@Autowired
	private LocationDao locationDao;

	@Autowired
	private TargetDao targetDao;
	
	public void saveSurvey(SurveyDTO surveyDTO) throws Exception {
		try {
			Survey surveyToUpdate = surveyDao.getSurveyById(surveyDTO.getId());
			if (surveyToUpdate != null) {
				Organisation organisation = organisationDao.getOrganisation(surveyDTO.getOrganisation());
				if (organisation == null) {
					throw new ItemNotFoundException("Organisation not found", "Your organisation was not found. Please contact the system administrator to register your organisation");
				}
				surveyToUpdate.setTitle(surveyDTO.getTitle());
				surveyToUpdate.setDescription(surveyDTO.getDescription());
				surveyToUpdate.setStartDate(surveyDTO.getStartDate());
				surveyToUpdate.setEndDate(surveyDTO.getEndDate());
				List<Question> questions = new ArrayList<Question>();
				for (QuestionDTO question : surveyDTO.getQuestions()) {
					questions.add(new Question(surveyToUpdate, question));
				}
				surveyToUpdate.setQuestions(questions);
				Log log = logDao.getLog(surveyDTO.getId());
				log.setName(surveyDTO.getTitle());
				log.setStartDate(surveyDTO.getStartDate());
				log.setEndDate(surveyDTO.getEndDate());
				Target target = targetDao.getTarget(surveyDTO.getTargetDTO().getId());
				if (target != null) {
					surveyToUpdate.setTarget(target);
					log.setTarget(target);
				} else {
					Target targetToSet = getTargetToSet(surveyDTO);
					surveyToUpdate.setTarget(targetToSet);
					log.setTarget(targetToSet);
				}
			} else {
				Log logToSave = null;
				Organisation organisation = organisationDao.getOrganisation(surveyDTO.getOrganisation());
				if (organisation == null) {
					throw new ItemNotFoundException("Organisation not found", "Your organisation was not found. Please contact the system administrator to register your organisation");
				}
				Survey survey = new Survey(surveyDTO);
				Target target = targetDao.getTarget(surveyDTO.getTargetDTO().getId());
				if (target != null) {
					survey.setTarget(target);
					logToSave = new Log(surveyDTO.getId(), surveyDTO.getTitle(), "campaigns", surveyDTO.getOrganisation(), surveyDTO.getStartDate(), surveyDTO.getEndDate(), target);
				} else {
					Target targetToSet = getTargetToSet(surveyDTO);
					survey.setTarget(targetToSet);
					logToSave = new Log(surveyDTO.getId(), surveyDTO.getTitle(), "campaigns", surveyDTO.getOrganisation(), surveyDTO.getStartDate(), surveyDTO.getEndDate(), targetToSet);
				}
				survey.setOrganisation(organisation);
				surveyDao.saveSurvey(survey);
				logDao.saveLog(logToSave);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private Target getTargetToSet(SurveyDTO surveyDTO) {
		Location location = locationDao.getLocation(surveyDTO.getTargetDTO().getLocationDTO());
		Target targetToSet = new Target(surveyDTO.getTargetDTO());
		targetToSet.setLocation(location);
		Target targetToCheck = targetDao.getTarget(targetToSet);
		if (targetToCheck == null)
			targetToSet = targetDao.saveTarget(targetToSet);
		return targetToSet;
	}

	public SurveyDTOLite getSurveyById(String surveyId, ExternalUser userMakingRequest) throws Exception {
		Assert.notNull(userMakingRequest);
		Assert.notNull(surveyId);
		if (!userMakingRequest.getRole().equalsIgnoreCase(Role.authenticated.toString())) {
			throw new AuthorizationException("You are not allowed to access the survey. Please login first");
		}
		Survey survey = null;
		try {
			survey = surveyDao.getSurveyById(surveyId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new SurveyDTOLite(survey);
	}

	public List<SurveyRequest> getRunningSurveys(ExternalUser userMakingRequest) throws Exception {
		Assert.notNull(userMakingRequest);
		List<SurveyRequest> runningSurveys = new ArrayList<SurveyRequest>();
		List<Survey> surveyList = new ArrayList<Survey>();
		try {
			surveyList = surveyDao.getRunningSurveys();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (userMakingRequest.getRole().equals(Role.authenticated.toString())) {
			for (Survey survey : surveyList) {
				if (survey.getTarget() != null) {
					if (targetMatch(userMakingRequest, survey.getTarget()))
						runningSurveys.add(new SurveyRequest(survey));
				} else {
					runningSurveys.add(new SurveyRequest(survey));
				}
			}
		} else {
			for (Survey survey : surveyList) {
				runningSurveys.add(new SurveyRequest(survey));
			}
		}
		return runningSurveys;
	}
	
}