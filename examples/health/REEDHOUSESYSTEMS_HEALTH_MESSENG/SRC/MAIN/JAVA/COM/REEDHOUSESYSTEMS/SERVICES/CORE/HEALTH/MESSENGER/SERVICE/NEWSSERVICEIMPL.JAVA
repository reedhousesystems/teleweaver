package com.reedhousesystems.services.core.health.messenger.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reedhousesystems.services.core.health.messenger.api.DemographicDao;
import com.reedhousesystems.services.core.health.messenger.api.LocationDao;
import com.reedhousesystems.services.core.health.messenger.api.LogDao;
import com.reedhousesystems.services.core.health.messenger.api.NewsDao;
import com.reedhousesystems.services.core.health.messenger.api.NewsService;
import com.reedhousesystems.services.core.health.messenger.api.OrganisationDao;
import com.reedhousesystems.services.core.health.messenger.api.TargetDao;
import com.reedhousesystems.services.core.health.messenger.api.UserDao;
import com.reedhousesystems.services.core.health.messenger.dto.NewsDTO;
import com.reedhousesystems.services.core.health.messenger.dto.NewsDTOLite;
import com.reedhousesystems.services.core.health.messenger.exception.ItemNotFoundException;
import com.reedhousesystems.services.core.health.messenger.model.ExternalUser;
import com.reedhousesystems.services.core.health.messenger.model.Location;
import com.reedhousesystems.services.core.health.messenger.model.Log;
import com.reedhousesystems.services.core.health.messenger.model.News;
import com.reedhousesystems.services.core.health.messenger.model.Organisation;
import com.reedhousesystems.services.core.health.messenger.model.Target;
import com.reedhousesystems.services.core.health.messenger.model.User;

@Service("newsService")
@Transactional
public class NewsServiceImpl extends BaseService implements NewsService {

	@Autowired
	private NewsDao newsDao;
	@Autowired
	private UserDao userDao;
	@Resource
	private OrganisationDao organisationDao;
	@Resource
	private LogDao logDao;
	@Autowired
	private TargetDao targetDao;
	@Autowired
	private LocationDao locationDao;
	@Autowired
	private DemographicDao demographicDao;

	public Integer saveNews(NewsDTO newsDTO) {
		News newsToUpdate = newsDao.getNewsById(newsDTO.getId());
		Integer numberOfMatchingUsers = null;
		if (newsToUpdate != null) {
			Organisation organisation = organisationDao.getOrganisation(newsDTO.getOrganisation());
			if (organisation == null) {
				throw new ItemNotFoundException("Organisation not found", "Your organisation was not found. Please contact the system administrator to register your organisation");
			}
			newsToUpdate.setTitle(newsDTO.getTitle());
			newsToUpdate.setContent(newsDTO.getContent());
			newsToUpdate.setAuthor(newsDTO.getAuthor());
			newsToUpdate.setStatus(newsDTO.getStatus());
			Log log = logDao.getLog(newsDTO.getId());
			log.setName(newsDTO.getTitle());
			Target target = targetDao.getTarget(newsDTO.getTargetDTO().getId());
			if (target != null) {
				newsToUpdate.setTarget(target);
				log.setTarget(target);
			} else {
				Target targetToSet = getTargetToSet(newsDTO);
				newsToUpdate.setTarget(targetToSet);
				log.setTarget(targetToSet);
			}
			numberOfMatchingUsers = demographicDao.getNumberOfMatchingUsers(newsToUpdate.getTarget()) != null ? demographicDao.getNumberOfMatchingUsers(newsToUpdate.getTarget()).intValue() : null;
		} else {
			Log logToSave = null;
			Organisation organisation = organisationDao.getOrganisation(newsDTO.getOrganisation());
			if (organisation == null) {
				throw new ItemNotFoundException("Organisation not found", "Your organisation was not found. Please contact the system administrator to register your organisation");
			}
			News news = new News(newsDTO);
			Target target = targetDao.getTarget(newsDTO.getTargetDTO().getId());
			if (target != null) {
				news.setTarget(target);
				logToSave = new Log(newsDTO.getId(), newsDTO.getTitle(), "news", newsDTO.getOrganisation(), null, null, target);
			} else {
				Target targetToSet = getTargetToSet(newsDTO);
				news.setTarget(targetToSet);
				logToSave = new Log(newsDTO.getId(), newsDTO.getTitle(), "campaigns", newsDTO.getOrganisation(), null, null, targetToSet);
			}
			news.setOrganisation(organisation);
			newsDao.saveNews(news);
			logDao.saveLog(logToSave);
			numberOfMatchingUsers = demographicDao.getNumberOfMatchingUsers(news.getTarget()) != null ? demographicDao.getNumberOfMatchingUsers(news.getTarget()).intValue() : null;
		}
		return numberOfMatchingUsers;
	}
	
	private Target getTargetToSet(NewsDTO newsDTO) {
		Location location = locationDao.getLocation(newsDTO.getTargetDTO().getLocationDTO());
		Target targetToSet = new Target(newsDTO.getTargetDTO());
		targetToSet.setLocation(location);
		Target targetToCheck = targetDao.getTarget(targetToSet);
		if (targetToCheck == null)
			targetToSet = targetDao.saveTarget(targetToSet);
		return targetToSet;
	}

	public NewsDTOLite getNewsById(String newsId, ExternalUser userMakingRequest) throws Exception {
		News news = null;
		try {
			news = newsDao.getNewsById(newsId);
			User user = userDao.getUserByUUId(userMakingRequest.getId());
			List<User> users = news.getUsers();
			if (!users.contains(user)) {
				users.add(user);
				news.setUsers(users);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new NewsDTOLite(news);
	}

	public List<NewsDTOLite> getActiveNews(ExternalUser userMakingRequest) {
		List<News> newsList = newsDao.getActiveNews();
		List<NewsDTOLite> listOfNews = new ArrayList<NewsDTOLite>();
		if (userMakingRequest != null) {
			if (userMakingRequest.getRole().equals("authenticated")) {
				for (News news : newsList) {
					if (news.getTarget() != null) {
						if (targetMatch(userMakingRequest, news.getTarget()))
							listOfNews.add(new NewsDTOLite(news));
					} else {
						listOfNews.add(new NewsDTOLite(news));
					}
				}
			}
		}
		return listOfNews;
	}
}