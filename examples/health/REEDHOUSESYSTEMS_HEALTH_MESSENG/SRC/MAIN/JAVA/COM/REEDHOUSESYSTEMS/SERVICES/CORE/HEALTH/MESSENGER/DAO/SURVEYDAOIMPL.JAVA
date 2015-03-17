package com.reedhousesystems.services.core.health.messenger.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.reedhousesystems.services.core.health.messenger.api.SurveyDao;
import com.reedhousesystems.services.core.health.messenger.model.Question;
import com.reedhousesystems.services.core.health.messenger.model.Survey;

@Repository("surveyDao")
public class SurveyDaoImpl extends BaseDao implements SurveyDao {

	public void saveSurvey(Survey survey) throws Exception {
		try {
			getSession().save(survey);
		} catch (HibernateException hex) {
			throw new Exception("Error saving a survey", hex);
		}
	}

	public Survey getSurveyById(String surveyId) throws Exception {
		Criteria criteria = getSession().createCriteria(Survey.class)
			.add(Restrictions.eq("uuid", surveyId));
		Survey survey = (Survey) criteria.uniqueResult();
		if (survey != null) {
			for (Question question : survey.getQuestions()) {
				Hibernate.initialize(question.getAnswerOptions());
			}
		}
		return survey;
	}

	public List<Survey> getRunningSurveys() throws Exception {
		List<Survey> runningSurveys = new ArrayList<Survey>();
		try {
//			Date currentDate = new Date();
			Criteria criteria = getSession().createCriteria(Survey.class)
				/*.add(Restrictions.eq("status", "Active"))
				.add(Restrictions.le("startDate", currentDate))
				.add(Restrictions.ge("endDate", currentDate))*/
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			runningSurveys = criteria.list();
		} catch (HibernateException hex) {
			throw new Exception("Error getting running surveys", hex);
		}
		return runningSurveys;
	}
	
}