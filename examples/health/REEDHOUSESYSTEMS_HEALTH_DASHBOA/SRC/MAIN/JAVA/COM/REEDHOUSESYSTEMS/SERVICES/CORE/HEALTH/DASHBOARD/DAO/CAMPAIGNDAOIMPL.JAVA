package com.reedhousesystems.services.core.health.dashboard.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.reedhousesystems.services.core.health.dashboard.api.CampaignDao;
import com.reedhousesystems.services.core.health.dashboard.dto.UUIDDTO;
import com.reedhousesystems.services.core.health.dashboard.exception.ItemNotFoundException;
import com.reedhousesystems.services.core.health.dashboard.model.Campaign;
import com.reedhousesystems.services.core.health.dashboard.model.Message;
import com.reedhousesystems.services.core.health.dashboard.model.Organisation;
import com.reedhousesystems.services.core.health.dashboard.model.Target;

@Repository("campaignDao")
public class CampaignDaoImpl extends BaseDao implements CampaignDao {
    
	public UUIDDTO saveCampaign(Campaign campaign) throws Exception {
		try {
			getSession().save(campaign);
		} catch (HibernateException ex) {
			throw new Exception("An error occured while adding a campaign", ex);
		}
		return new UUIDDTO(campaign.getUuid().toString());
	}
	
	public void deleteCampaign(String campaignId) throws Exception {
		try {
			Campaign campaignToDelete = getCampaignById(campaignId);
			getSession().delete(campaignToDelete);
		} catch (HibernateException e) {
			throw new Exception("An error occured while deleting a campaign", e);
		}
	}
	
	public Campaign getCampaignById(String campaignId) throws Exception {
		Campaign campaign = null;
		try {
			Criteria criteria = getSession().createCriteria(Campaign.class)
				.add(Restrictions.eq("uuid", campaignId));
			campaign = (Campaign) criteria.uniqueResult();
		} catch (HibernateException e) {
			throw new Exception("An error occurred while getting a campaign");
		}
		if (campaign == null)
			throw new ItemNotFoundException("Campaign Not Found", "Campaign requested cannot be found");
		return campaign;
	}
	
	public List<Campaign> getAllCampaigns(Organisation organisation, String role) throws Exception {
		List<Campaign> campaignList = new ArrayList<Campaign>();
		try {
			Criteria criteria = getSession().createCriteria(Campaign.class)
				.setProjection(Projections.distinct(Projections.projectionList()
					.add(Projections.property("uuid"))
					.add(Projections.property("title"))
					.add(Projections.property("startDate"))
					.add(Projections.property("endDate"))
					.add(Projections.property("status"))
					.add(Projections.property("slogan"))
					.add(Projections.property("aim"))
					.add(Projections.property("description"))));
			if (role == "authenticated")
				criteria.add(Restrictions.eq("organisation", organisation));
			campaignList = criteria.list();
		} catch (HibernateException e) {
			throw new Exception("An error occured while getting all the campaigns", e);
		}
		return campaignList;
	}
	
	public boolean campaignTargetExists(String campaignId) throws Exception {
		Campaign campaign = getCampaignById(campaignId);
		Target target = campaign.getTarget();
		if (target == null) return false;
		return true;
	}
	
	public boolean messageExisits(String messageId) throws Exception {
		try {
			Message message = getMessageById(messageId);
			if (message != null) return true;
			else return false;
		} catch (HibernateException e) {
			throw new Exception("An error occurred while checking existence of a message");
		}
	}
	
	public void saveMessage(String campaignId, Message message) throws Exception {
		try {
			Campaign campaign = getCampaignById(campaignId);
			message.setCampaign(campaign);
			getSession().save(message);
		} catch (HibernateException e) {
			throw new Exception("An error occured while adding a message", e);
		}
	}
	
	public void deleteMessage(String messageId) throws Exception {
		try {
			Message message = getMessageById(messageId);
			getSession().delete(message);
		} catch (HibernateException e) {
			throw new Exception("An error occured while deleting a message", e);
		}
	}
	
	public Message getMessageById(String messageId) throws Exception {
		Message message = null;
		try {
			Criteria criteria = getSession().createCriteria(Message.class)
				.add(Restrictions.eq("uuid", messageId));
			message = (Message) criteria.uniqueResult();
		} catch (HibernateException hex) {
			throw new Exception("An error occurred while loading a messages");
		}
		if (message == null) throw new ItemNotFoundException("Message Not Found", "Message requested cannot be found");
		return message;
	}
	
	public List<Message> getMessagesByCampaignId(String campaignId) throws Exception {
		List<Message> messageList = new ArrayList<Message>();
		try {
			Campaign campaign = getCampaignById(campaignId);
			Criteria criteria = getSession().createCriteria(Message.class)
					.add(Restrictions.eq("campaign", campaign))
					.setProjection(Projections.distinct(Projections.projectionList()
					.add(Projections.property("item"))
					.add(Projections.property("description"))
					.add(Projections.property("uuid"))));
			messageList = criteria.list();
		} catch (HibernateException e) {
			throw new Exception("An error occured while getting messages y campaign Id", e);
		}
		return messageList;
	}

	public List<Campaign> getCampaignsToReport(Organisation organisation, String role) throws Exception {
		List<Campaign> campaignList = new ArrayList<Campaign>();
		try {
			Criteria criteria = getSession().createCriteria(Campaign.class)
				.add(Restrictions.in("status", Arrays.asList("Published", "Archived")))
				.setProjection(Projections.distinct(Projections.projectionList()
				.add(Projections.property("title"))
				.add(Projections.property("numberOfViews"))));
			if (role == "authenticated")
				criteria.add(Restrictions.eq("organisation", organisation));
			campaignList = criteria.list();
		} catch (HibernateException e) {
			throw new Exception("An error occured while getting campaigns to report", e);
		}
		return campaignList;
	}

	public List<Campaign> getArchivedCampaigns(Organisation organisation, String role) throws Exception {
		List<Campaign> campaignList = new ArrayList<Campaign>();
		try {
			Criteria criteria = getSession().createCriteria(Campaign.class)
				.add(Restrictions.eq("status", "Archived"))
				.setProjection(Projections.distinct(Projections.projectionList()
				.add(Projections.property("title"))
				.add(Projections.property("numberOfViews"))));
			if (role == "authenticated")
				criteria.add(Restrictions.eq("organisation", organisation));
			campaignList = criteria.list();
		} catch (HibernateException e) {
			throw new Exception("An error occured while getting campaigns to report", e);
		}
		return campaignList;
	}
}