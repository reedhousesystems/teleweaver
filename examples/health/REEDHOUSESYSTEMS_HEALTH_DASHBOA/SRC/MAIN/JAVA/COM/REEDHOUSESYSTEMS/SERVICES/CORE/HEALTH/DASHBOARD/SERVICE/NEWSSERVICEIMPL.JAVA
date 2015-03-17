package com.reedhousesystems.services.core.health.dashboard.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reedhousesystems.services.core.health.dashboard.api.LocationDao;
import com.reedhousesystems.services.core.health.dashboard.api.NewsDao;
import com.reedhousesystems.services.core.health.dashboard.api.NewsService;
import com.reedhousesystems.services.core.health.dashboard.api.TargetDao;
import com.reedhousesystems.services.core.health.dashboard.dto.NewsDTO;
import com.reedhousesystems.services.core.health.dashboard.dto.StatusDTO;
import com.reedhousesystems.services.core.health.dashboard.dto.TargetDTO;
import com.reedhousesystems.services.core.health.dashboard.exception.ItemNotFoundException;
import com.reedhousesystems.services.core.health.dashboard.model.Location;
import com.reedhousesystems.services.core.health.dashboard.model.News;
import com.reedhousesystems.services.core.health.dashboard.model.Target;
import com.reedhousesystems.services.core.health.dashboard.requests.NewsRequest;

@Service("newsService")
@Transactional
public class NewsServiceImpl implements NewsService {
	
	@Resource
	private NewsDao newsDao;
    
    @Resource
	private TargetDao targetDao;
    
    @Resource
    private LocationDao locationDao;
	
	public NewsRequest saveNews(NewsRequest request) throws Exception {
		NewsRequest savedNews = null;
		try {
			News newsToSave = createNews(request);
			savedNews = newsDao.saveNews(newsToSave);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return savedNews;
	}

	public void updateNews(String newsId, NewsRequest request) throws Exception {
		try {
			News newsToUpdate = newsDao.getNewsById(newsId);
			if (request.getAuthor() != null) newsToUpdate.setAuthor(request.getAuthor());
			if (request.getTitle() != null) newsToUpdate.setTitle(request.getTitle());
			if (request.getContent() != null) newsToUpdate.setContent(request.getContent());
			if (request.getTarget() != null) newsToUpdate.setTarget(request.getTarget());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateNews(String newsId, StatusDTO statusDTO) throws Exception {
		try {
			News newToUpdate = newsDao.getNewsById(newsId);
			newToUpdate.setStatus(statusDTO.getStatus());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteNews(String newsId) throws Exception {
		try {
			newsDao.deleteNews(newsId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public NewsRequest getNewsById(String newsId) throws Exception {
		News news = null;
		try {
			news = newsDao.getNewsById(newsId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new NewsRequest(news);
	}
	
	public NewsDTO getNewsToPublish(String newsId) throws Exception {
		News news = null;
		try {
			news = newsDao.getNewsById(newsId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new NewsDTO(news);
	}

	public List<NewsDTO> getAllNews() throws Exception {
		List<NewsDTO> newsList = new ArrayList<NewsDTO>();
		List<News> listOfNews = new ArrayList<News>();
		 try {
			 listOfNews = newsDao.getAllNews();
			for (News news : listOfNews) {
				NewsDTO newsDTO = new NewsDTO(news);
				newsList.add(newsDTO);
			}
		 } catch (Exception ex) {
			ex.printStackTrace();
		 }
		 return newsList;
	}

	public void setNewsTarget(String newsId, TargetDTO targetDTO) throws Exception {
		try {
			News news = newsDao.getNewsById(newsId);
			if (news == null)
				throw new ItemNotFoundException("News not found", "News item to set target cannot be found");
			Location location = locationDao.getLocation(targetDTO.getLocationDTO());
			Target targetToCheck = new Target(targetDTO);
			targetToCheck.setLocation(location);
			Target target = targetDao.getTarget(targetToCheck);
			if (target == null)
				target = targetDao.saveTarget(targetToCheck);
			news.setTarget(target);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public Target getNewsTarget(String newsId) throws Exception {
		Target target = null;
		try {
			News news = newsDao.getNewsById(newsId);
			target = news.getTarget();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return target;
	}
	
	private News createNews(NewsRequest request) {
		Date now = new Date();
		News news = new News(request);
		news.setCreationDate(now);
		news.setStatus("Active");
		return news;
	}
	
}