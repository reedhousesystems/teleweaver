package com.reedhousesystems.services.core.health.dashboard.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.reedhousesystems.services.core.health.dashboard.api.NewsDao;
import com.reedhousesystems.services.core.health.dashboard.model.News;
import com.reedhousesystems.services.core.health.dashboard.requests.NewsRequest;

@Repository("newsDao")
public class NewsDaoImpl extends BaseDao implements NewsDao {
	
	public NewsRequest saveNews(News news) throws Exception {
		try {
			getSession().save(news);
		} catch (HibernateException e) {
			throw new Exception("An error occured while adding or updating news", e);
		}
		return new NewsRequest(news);
	}
	
	public void deleteNews(String newsId) throws Exception {
		News newsToDelete = getNewsById(newsId);
		try {
			getSession().delete(newsToDelete);
		} catch (HibernateException e) {	
			throw new Exception("An error occured while deleting news", e);
		}
	}
	
	public News getNewsById(String newsId) throws Exception {
		Criteria criteria = getSession().createCriteria(News.class)
			.add(Restrictions.eq("uuid", newsId));
		News news = (News) criteria.uniqueResult();
		return news;
	}
	
	public List<News> getAllNews() throws Exception {
		List<News> newsList = new ArrayList<News>();
		try {
			Criteria criteria = getSession().createCriteria(News.class)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			newsList = criteria.list();
		} catch (HibernateException e) {	
			throw new Exception("An error occurred while getting all news articles", e);
		}
		return newsList;
	}
	
}