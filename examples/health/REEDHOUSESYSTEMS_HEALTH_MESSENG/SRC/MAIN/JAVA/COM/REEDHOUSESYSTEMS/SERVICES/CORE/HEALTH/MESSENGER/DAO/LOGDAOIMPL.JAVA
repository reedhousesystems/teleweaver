package com.reedhousesystems.services.core.health.messenger.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.reedhousesystems.services.core.health.messenger.api.LogDao;
import com.reedhousesystems.services.core.health.messenger.model.Log;

@Repository("logDao")
public class LogDaoImpl extends BaseDao implements LogDao {

	public void saveLog(Log log) {
		getSession().save(log);
	}

	public void deleteLog(Log log) {
		getSession().delete(log);
	}

	public Log getLog(String logId) {
		Criteria criteria = getSession().createCriteria(Log.class)
			.add(Restrictions.eq("identifier", logId));
		Log log = (Log) criteria.uniqueResult();
		return log;
	}

	public List<Log> getLogsByOrganisationId(String organisationId) {
		Criteria criteria = getSession().createCriteria(Log.class)
			.add(Restrictions.eq("organisationId", organisationId));
		List<Log> logList = criteria.list();
		return logList;
	}
	
	public List<Log> getAll() {
		Criteria criteria = getSession().createCriteria(Log.class)
			/*.add(Restrictions.ge("startDate", getFormattedFromDateTime(cal.add(Calendar.DATE, 0))))
			.add(Restrictions.le("endDate", getFormattedToDateTime(currentDate)))*/
			.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<Log> logList = criteria.list();
		return logList;
	}
	
	private Date getFormattedFromDateTime(Date date) {
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    cal.set(Calendar.HOUR_OF_DAY, 0);
	    cal.set(Calendar.MINUTE, 0);
	    cal.set(Calendar.SECOND, 0);
	    return cal.getTime();
	}
	 
	private Date getFormattedToDateTime(Date date) {
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    cal.set(Calendar.HOUR_OF_DAY, 23);
	    cal.set(Calendar.MINUTE, 59);
	    cal.set(Calendar.SECOND, 59);
	    return cal.getTime();
	}
}