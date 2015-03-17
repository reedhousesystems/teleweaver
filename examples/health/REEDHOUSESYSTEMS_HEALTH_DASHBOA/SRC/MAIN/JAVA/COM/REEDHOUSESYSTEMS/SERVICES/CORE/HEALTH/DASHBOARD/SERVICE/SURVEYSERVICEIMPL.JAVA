package com.reedhousesystems.services.core.health.dashboard.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reedhousesystems.services.core.health.dashboard.api.LocationDao;
import com.reedhousesystems.services.core.health.dashboard.api.SurveyDao;
import com.reedhousesystems.services.core.health.dashboard.api.SurveyService;
import com.reedhousesystems.services.core.health.dashboard.api.TargetDao;
import com.reedhousesystems.services.core.health.dashboard.dto.PublishSurveyDTO;
import com.reedhousesystems.services.core.health.dashboard.dto.TargetDTO;
import com.reedhousesystems.services.core.health.dashboard.dto.UUIDDTO;
import com.reedhousesystems.services.core.health.dashboard.exception.ItemNotFoundException;
import com.reedhousesystems.services.core.health.dashboard.model.AnswerOptions;
import com.reedhousesystems.services.core.health.dashboard.model.Location;
import com.reedhousesystems.services.core.health.dashboard.model.Question;
import com.reedhousesystems.services.core.health.dashboard.model.Survey;
import com.reedhousesystems.services.core.health.dashboard.model.Target;
import com.reedhousesystems.services.core.health.dashboard.requests.AnswerOptionsRequest;
import com.reedhousesystems.services.core.health.dashboard.requests.QuestionRequest;
import com.reedhousesystems.services.core.health.dashboard.requests.SurveyRequest;

@Service("surveyService")
@Transactional
public class SurveyServiceImpl implements SurveyService {
	
	@Resource
	private SurveyDao surveyDao;
    
    @Resource
	private TargetDao targetDao;
    
    @Resource
    private LocationDao locationDao;
	
	public UUIDDTO saveSurvey(SurveyRequest request) throws Exception {
		UUIDDTO uuidDTO = null;
		try {
			Survey newSurvey = createNewSurvey(request);
			uuidDTO = new UUIDDTO(newSurvey.getUuid().toString());
			surveyDao.saveSurvey(newSurvey);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return uuidDTO;
	}

	public Survey updateSurvey(String surveyId, SurveyRequest request) throws Exception {
		Survey resultSurvey = null;
		try {
			Survey surveyToUpdate = surveyDao.getSurveyById(surveyId);
			if (request.getTitle() != null) surveyToUpdate.setTitle(request.getTitle());
			if (request.getDescription() != null) surveyToUpdate.setDescription(request.getDescription());
			if (request.getStartDate() != null) surveyToUpdate.setStartDate(request.getStartDate());
			if (request.getEndDate() != null) surveyToUpdate.setEndDate(request.getEndDate());
			return surveyToUpdate;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return resultSurvey;
	}

	public void updateSurvey(String surveyId, String status) throws Exception {
		try {
			Survey surveyToUpdate = surveyDao.getSurveyById(surveyId);
			surveyToUpdate.setStatus(status);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void deleteSurvey(String surveyId) throws Exception {
		try {
			surveyDao.deleteSurvey(surveyId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public SurveyRequest getSurveyById(String surveyId) throws Exception {
		Survey survey = new Survey();
		try {
			survey = surveyDao.getSurveyById(surveyId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new SurveyRequest(survey);
	}

	public PublishSurveyDTO getSurveyToPublish(String surveyId) throws Exception {
		Survey survey = new Survey();
		try {
			survey = surveyDao.getSurveyById(surveyId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new PublishSurveyDTO(survey);
	}

	/*public List<SurveyRequest> getAllSurveys() throws Exception {
		List<Survey> listOfSurveys = new ArrayList<Survey>();
		List<SurveyRequest> surveyList = new ArrayList<SurveyRequest>();
		try {
			listOfSurveys = surveyDao.getAllSurveys();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		for (Survey survey : listOfSurveys) {
			SurveyRequest surveyRequest = new SurveyRequest(survey);
			surveyList.add(surveyRequest);
		}
		return surveyList;
	}*/

	public List<Survey> getAllSurveys() throws Exception {
		List<Survey> listOfSurveys = new ArrayList<Survey>();
		try {
			listOfSurveys = surveyDao.getAllSurveys();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return listOfSurveys;
	}
	
	public void setSurveyTarget(String surveyId, TargetDTO targetDTO) throws Exception {
		try {
			Survey surveyToUpdate = surveyDao.getSurveyById(surveyId);
			if (surveyToUpdate == null)
				throw new ItemNotFoundException("Survey not found", "Survey to set target cannot be found");
			Location location = locationDao.getLocation(targetDTO.getLocationDTO());
			if (location == null)
				throw new ItemNotFoundException("Location not found", "Location specified cannot be found");
			Target targetToCheck = new Target(targetDTO);
			targetToCheck.setLocation(location);
			Target target = targetDao.getTarget(targetToCheck);
			if (target == null)
				target = targetDao.saveTarget(targetToCheck);
			surveyToUpdate.setTarget(target);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public Target getSurveyTarget(String surveyId) throws Exception {
		Target target = null;
		try {
			target = surveyDao.getSurveyTarget(surveyId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return target;
	}

	public QuestionRequest saveQuestion(String surveyId, QuestionRequest question) throws Exception {
		Question questionToSave = null;
		try {
			Survey survey = surveyDao.getSurveyById(surveyId);
			questionToSave = new Question(survey, question);
			List<Question> questions = survey.getQuestions();
			questions.add(questionToSave);
			survey.setQuestions(questions);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new QuestionRequest(questionToSave);
	}

	public QuestionRequest updateQuestion(long questionId, QuestionRequest question) throws Exception {
		Question questionToUpdate = null;
		try {
			questionToUpdate = surveyDao.getQuestionById(questionId);
			if (question.getQuestion() != null) questionToUpdate.setQuestion(question.getQuestion());
			if (question.getQuestionType() != null) questionToUpdate.setQuestionType(question.getQuestionType());
			if (question.getAnswerOptions() != null) {
				List<AnswerOptions> answerOptions = new ArrayList<AnswerOptions>();
				for (AnswerOptionsRequest answerOptionsRequest : question.getAnswerOptions()) {
					answerOptions.add(new AnswerOptions(answerOptionsRequest.getId(), answerOptionsRequest));
				}
				questionToUpdate.setAnswerOptions(answerOptions);
			}
			 
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new QuestionRequest(questionToUpdate);
	}

	public void deleteQuestion(long questionId, String surveyId) throws Exception {
		try {
			surveyDao.deleteQuestion(questionId, surveyId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public QuestionRequest getQuestionById(long questionId) throws Exception {
		Question question = null;
		try {
			question = surveyDao.getQuestionById(questionId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new QuestionRequest(question);
	}
	
	public List<Question> getQuestionsBySurveyId(String surveyId) throws Exception {
		List<Question> questionList = new ArrayList<Question>();
		try {
			questionList = surveyDao.getQuestionsBySurveyId(surveyId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return questionList;
	}
	
	private Survey createNewSurvey(SurveyRequest request) {
		Date now = new Date();
		Survey newSurvey = new Survey(request);
		newSurvey.setCreationDate(now);
		newSurvey.setStatus("Active");
		return newSurvey;
	}
	
}