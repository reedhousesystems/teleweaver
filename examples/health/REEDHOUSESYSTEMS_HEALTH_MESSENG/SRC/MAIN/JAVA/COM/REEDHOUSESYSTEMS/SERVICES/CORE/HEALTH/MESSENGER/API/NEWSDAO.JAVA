package com.reedhousesystems.services.core.health.messenger.api;

import java.util.List;

import com.reedhousesystems.services.core.health.messenger.model.News;

public interface NewsDao {
	
	public void saveNews(News news);
	
	public News getNewsById(String newsId);
	
	public List<News> getActiveNews();
}