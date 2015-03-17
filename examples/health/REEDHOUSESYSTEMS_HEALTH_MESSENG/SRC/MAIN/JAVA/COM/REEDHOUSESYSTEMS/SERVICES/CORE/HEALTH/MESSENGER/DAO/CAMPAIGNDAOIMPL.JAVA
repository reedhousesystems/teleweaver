package com.reedhousesystems.services.core.health.messenger.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.reedhousesystems.services.core.health.messenger.api.CampaignDao;
import com.reedhousesystems.services.core.health.messenger.model.Campaign;
import com.reedhousesystems.services.core.health.messenger.model.Message;

@Repository("campaignDao")
public class CampaignDaoImpl extends BaseDao implements CampaignDao {

	public void saveCampaign(Campaign campaign) {
		getSession().save(campaign);
	}
	
	public void deleteMessages(Message message) {
		getSession().delete(message);
	}

	public Campaign getCampaignById(String campaignId) {
		Criteria criteria = getSession().createCriteria(Campaign.class)
			.add(Restrictions.eq("uuid", campaignId));
		Campaign campaign = (Campaign) criteria.uniqueResult();
		return campaign;
	}

	public List<Campaign> getRunningCampaigns() {
		List<Campaign> campaignList = null;
		Criteria criteria = getSession().createCriteria(Campaign.class)
//			.add(Restrictions.eq("status", "Active"))
//			.add(Restrictions.ge("startDate", new Date()))
//			.add(Restrictions.le("endDate", new Date()))
			.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		campaignList = criteria.list();
		return campaignList;
	}

	public List<Message> getMessagesByCampaignId(String campaignId) {
		List<Message> messageList = null;
		Campaign campaign = getCampaignById(campaignId);
		Hibernate.initialize(campaign.getMessages());
		messageList = campaign.getMessages();
		return messageList;
	}
}