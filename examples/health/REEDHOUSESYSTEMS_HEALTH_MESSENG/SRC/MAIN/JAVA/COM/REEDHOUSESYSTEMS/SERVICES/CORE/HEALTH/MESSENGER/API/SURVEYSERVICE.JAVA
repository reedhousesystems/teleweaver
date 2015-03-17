package com.reedhousesystems.services.core.health.messenger.api;

import java.util.List;

import com.reedhousesystems.services.core.health.messenger.dto.SurveyDTO;
import com.reedhousesystems.services.core.health.messenger.dto.SurveyDTOLite;
import com.reedhousesystems.services.core.health.messenger.model.ExternalUser;
import com.reedhousesystems.services.core.health.messenger.request.SurveyRequest;

public interface SurveyService {
	
	public void saveSurvey(SurveyDTO survey) throws Exception;
	
	public SurveyDTOLite getSurveyById(String surveyId, ExternalUser userMakingRequest) throws Exception;
	
	public List<SurveyRequest> getRunningSurveys(ExternalUser userMakingRequest) throws Exception;
}