package com.reedhousesystems.services.core.health.messenger.api;

import java.util.List;

import org.springframework.stereotype.Service;

import com.reedhousesystems.services.core.health.messenger.dto.NewsDTO;
import com.reedhousesystems.services.core.health.messenger.dto.NewsDTOLite;
import com.reedhousesystems.services.core.health.messenger.model.ExternalUser;

@Service
public interface NewsService {
	
	public Integer saveNews(NewsDTO newsDTO);
	
	public NewsDTOLite getNewsById(String newsId, ExternalUser userMakingRequest) throws Exception;
	
	public List<NewsDTOLite> getActiveNews(ExternalUser userMakingRequest);
}