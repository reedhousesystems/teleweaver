package com.reedhousesystems.services.core.health.messenger.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.reedhousesystems.services.core.health.messenger.api.UserDao;
import com.reedhousesystems.services.core.health.messenger.model.SessionToken;
import com.reedhousesystems.services.core.health.messenger.model.User;

@Repository("userDao")
public class UserDaoImpl extends BaseDao implements UserDao {
	public void saveOrUpdateUser(User user) {
		getSession().save(user);
	}

	public void updateUser(User user) {
		getSession().update(user);
	}

	public void deleteUser(User user) {
		getSession().delete(user);
	}

	public User getUserByUUId(String uuid) {
		User user = null;
		Criteria criteria = getSession().createCriteria(User.class)
			.add(Restrictions.eq("uuid", uuid));
		user = (User) criteria.uniqueResult();
		return user;
	}
	
	public User getUserByUserName(String userName) {
		User user = null;
		Criteria criteria = getSession().createCriteria(User.class)
			.add(Restrictions.eq("userName", userName));
		user = (User)  criteria.uniqueResult();
		return user;
	}

	public List<User> getAllUsers() {
		List<User> userList = new ArrayList<User>();
		Criteria criteria = getSession().createCriteria(User.class);
		userList = criteria.list();
		return userList;
	}
	
	public List<User> getUsersWithExpiredTokens(Date date) {
		List<User> usersWithExpiredTokens = new ArrayList<User>();
		Criteria criteria = getSession().createCriteria(SessionToken.class)
			.add(Restrictions.lt("lastUpdated", date));
		List<SessionToken> expiredTokens = criteria.list();
		for (SessionToken sessionToken : expiredTokens) {
			usersWithExpiredTokens.add(sessionToken.getUser());
		}
		return usersWithExpiredTokens;
	}

	public User findBySessionToken(String token) {
		Criteria criteria = getSession().createCriteria(SessionToken.class)
			.add(Restrictions.eq("token", token));
		SessionToken sessionToken = (SessionToken) criteria.uniqueResult();
		User user = sessionToken.getUser();
		return user;
	}	
}