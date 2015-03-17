package com.reedhousesystems.services.core.health.dashboard.api;

import java.util.List;

import com.reedhousesystems.services.core.health.dashboard.dto.NewsDTO;
import com.reedhousesystems.services.core.health.dashboard.dto.StatusDTO;
import com.reedhousesystems.services.core.health.dashboard.dto.TargetDTO;
import com.reedhousesystems.services.core.health.dashboard.model.Target;
import com.reedhousesystems.services.core.health.dashboard.requests.NewsRequest;

public interface NewsService {
	
	public NewsRequest saveNews(NewsRequest request) throws Exception;
	
	public void updateNews(String newsId, NewsRequest request) throws Exception;
	
	public void updateNews(String newsId, StatusDTO statusDTO) throws Exception;
	
	public void deleteNews(String newsId) throws Exception;
	
	public NewsRequest getNewsById(String newsId) throws Exception;
	
	public NewsDTO getNewsToPublish(String newsId) throws Exception;
	
	public List<NewsDTO> getAllNews() throws Exception;
	
	public void setNewsTarget(String newsId, TargetDTO targetDTO) throws Exception;
	
	public Target getNewsTarget(String newsId) throws Exception;
	
}