package com.reedhousesystems.services.core.health.messenger.api;

import java.util.List;

import com.reedhousesystems.services.core.health.messenger.model.Survey;

public interface SurveyDao {
	
	public void saveSurvey(Survey survey) throws Exception;
	
	public Survey getSurveyById(String surveyId) throws Exception;
	
	public List<Survey> getRunningSurveys() throws Exception;
	
}