package com.reedhousesystems.services.core.health.dashboard.api;

import java.util.List;

import com.reedhousesystems.services.core.health.dashboard.dto.PublishSurveyDTO;
import com.reedhousesystems.services.core.health.dashboard.dto.TargetDTO;
import com.reedhousesystems.services.core.health.dashboard.dto.UUIDDTO;
import com.reedhousesystems.services.core.health.dashboard.model.Question;
import com.reedhousesystems.services.core.health.dashboard.model.Survey;
import com.reedhousesystems.services.core.health.dashboard.model.Target;
import com.reedhousesystems.services.core.health.dashboard.requests.QuestionRequest;
import com.reedhousesystems.services.core.health.dashboard.requests.SurveyRequest;

public interface SurveyService {
	
	public UUIDDTO saveSurvey(SurveyRequest request) throws Exception;
	
	public Survey updateSurvey (String surveyId, SurveyRequest request) throws Exception;
	
	public void updateSurvey (String surveyId, String status) throws Exception;
	
	public void deleteSurvey(String surveyId) throws Exception;
	
	public SurveyRequest getSurveyById(String surveyId) throws Exception;
	
	public PublishSurveyDTO getSurveyToPublish(String surveyId) throws Exception;
	
//	public List<SurveyRequest> getAllSurveys() throws Exception;
	
	public List<Survey> getAllSurveys() throws Exception;
	
	public void setSurveyTarget(String surveyId, TargetDTO targetDTO) throws Exception;
	
	public Target getSurveyTarget(String surveyId) throws Exception;
	
	public QuestionRequest saveQuestion(String surveyId, QuestionRequest question) throws Exception;
	
	public QuestionRequest updateQuestion(long questionId, QuestionRequest question) throws Exception;
	
	public void deleteQuestion(long questionId, String surveyId) throws Exception;
	
	public QuestionRequest getQuestionById(long questionId) throws Exception;
	
	public List<Question> getQuestionsBySurveyId(String surveyId) throws Exception;
	
}
