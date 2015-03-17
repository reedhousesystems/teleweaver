package com.reedhousesystems.services.core.health.dashboard.api;

import java.util.List;

import com.reedhousesystems.services.core.health.dashboard.model.Question;
import com.reedhousesystems.services.core.health.dashboard.model.Survey;
import com.reedhousesystems.services.core.health.dashboard.model.Target;

public interface SurveyDao {
	
	public void saveSurvey(Survey survey) throws Exception;
	
	public void deleteSurvey(String surveyId) throws Exception;
	
	public Survey getSurveyById(String surveyId) throws Exception;
	
	public List<Survey> getAllSurveys() throws Exception;
	
	public Target getSurveyTarget(String surveyId) throws Exception;
	
	public void saveQuestion(String surveyId, Question question) throws Exception;
	
	public void deleteQuestion(long questionId, String surveyId) throws Exception;
	
	public Question getQuestionById(long questionId) throws Exception;
	
	public List<Question> getQuestionsBySurveyId(String surveyId) throws Exception;
	
}