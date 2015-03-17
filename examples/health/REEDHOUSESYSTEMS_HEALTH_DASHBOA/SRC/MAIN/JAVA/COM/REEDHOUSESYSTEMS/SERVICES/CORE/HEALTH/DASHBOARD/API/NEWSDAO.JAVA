package com.reedhousesystems.services.core.health.dashboard.api;

import java.util.List;

import com.reedhousesystems.services.core.health.dashboard.model.News;
import com.reedhousesystems.services.core.health.dashboard.requests.NewsRequest;

public interface NewsDao {
	
	public NewsRequest saveNews(News news) throws Exception;
	
	public void deleteNews(String newsId) throws Exception;
	
	public News getNewsById(String newsId) throws Exception;
	
	public List<News> getAllNews() throws Exception;
	
}