package com.reedhousesystems.services.core.health.messenger.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.reedhousesystems.services.core.health.messenger.api.NewsDao;
import com.reedhousesystems.services.core.health.messenger.model.News;

@Repository("NewsDao")
public class NewsDaoImpl extends BaseDao implements NewsDao {

	public void saveNews(News news) {
		getSession().save(news);
	}
	
	public News getNewsById(String newsId) {
		Criteria criteria = getSession().createCriteria(News.class)
			.add(Restrictions.eq("uuid", newsId));
		News news = (News) criteria.uniqueResult();
		return news;
	}

	public List<News> getActiveNews() {
		List<News> newsList = new ArrayList<News>();
		Criteria criteria = getSession().createCriteria(News.class);
//			.add(Restrictions.eq("status", "Active"));
		newsList = criteria.list();
		return newsList;
	}
}