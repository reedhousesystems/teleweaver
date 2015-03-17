package com.reedhousesystems.services.core.health.dashboard.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.reedhousesystems.services.core.health.dashboard.api.SurveyDao;
import com.reedhousesystems.services.core.health.dashboard.model.Question;
import com.reedhousesystems.services.core.health.dashboard.model.Survey;
import com.reedhousesystems.services.core.health.dashboard.model.Target;

@Repository("surveyDao")
public class SurveyDaoImpl extends BaseDao implements SurveyDao {
	
	public void saveSurvey(Survey survey) throws Exception {
		try {
			getSession().save(survey);
		} catch (HibernateException ex) {
			throw new Exception("An error occured while adding a survey", ex);
		}
	}
	
	public void deleteSurvey(String surveyId) throws Exception {
		Survey surveyToDelete = getSurveyById(surveyId);
		getSession().delete(surveyToDelete);
	}
	
	public Survey getSurveyById(String surveyId) throws Exception {
		Criteria criteria = getSession().createCriteria(Survey.class)
			.add(Restrictions.eq("uuid", surveyId));
		Survey survey = (Survey) criteria.uniqueResult();
		for (Question question : survey.getQuestions()) {
			Hibernate.initialize(question.getAnswerOptions());
		}
		return survey;
	}
	
	public List<Survey> getAllSurveys() throws Exception {
		List<Survey> surveyList = new ArrayList<Survey>();
		try {
			Criteria criteria = getSession().createCriteria(Survey.class)
				.setProjection(Projections.distinct(Projections.projectionList()
					.add(Projections.property("title"))
					.add(Projections.property("creationDate"))
					.add(Projections.property("startDate"))
					.add(Projections.property("endDate"))
					.add(Projections.property("status"))
					.add(Projections.property("description"))
					.add(Projections.property("uuid"))));
			surveyList = criteria.list();
		} catch (HibernateException ex) {
			throw new Exception("An error occured while getting all the surveys", ex);
		}
		return surveyList;
	}
	
	public Target getSurveyTarget(String surveyId) throws Exception {
		Survey survey = getSurveyById(surveyId);
		return survey.getTarget();
	}
	
	public void saveQuestion(String surveyId, Question question) throws Exception {
		Survey survey = getSurveyById(surveyId);
		try {
			question.setSurvey(survey);
			getSession().save(question);
		} catch (HibernateException ex) {
			throw new Exception("An error occured while adding a survey question", ex);
		}
	}
	
	public void deleteQuestion(long questionId, String surveyId) throws Exception {
		Survey survey = getSurveyById(surveyId);
		Question question = getQuestionById(questionId);
		survey.getQuestions().remove(question);
		getSession().delete(question);
	}
	
	public Question getQuestionById(long questionId) throws Exception {
		Question question = (Question) getSession().get(Question.class, questionId);
		return question;
	}
	
	public List<Question> getQuestionsBySurveyId(String surveyId) throws Exception {
		List<Question> questionList = new ArrayList<Question>();
		try {
			Survey survey = getSurveyById(surveyId);
			questionList = survey.getQuestions();
			for (Question question : questionList) {
				Hibernate.initialize(question.getAnswerOptions());
			}
		} catch (HibernateException ex) {
			throw new Exception("An error occured while getting all the survey questions", ex);
		}
		return questionList;
	}
	
}