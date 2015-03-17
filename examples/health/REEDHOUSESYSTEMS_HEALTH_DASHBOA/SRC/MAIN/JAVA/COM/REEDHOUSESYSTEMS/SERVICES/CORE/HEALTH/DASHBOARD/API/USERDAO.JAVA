package com.reedhousesystems.services.core.health.dashboard.api;

import java.util.Date;
import java.util.List;

import com.reedhousesystems.services.core.health.dashboard.model.Organisation;
import com.reedhousesystems.services.core.health.dashboard.model.User;

public interface UserDao {
	
	public void saveOrUpdateUser(User user );
	
	public void deleteUser( User user );
	
	public User getUserByUUId( String uuid );
	
	public User getUserByUserName(String userName);
	
	public List<User> getUsersWithExpiredTokens(Date date);
	
	public List<User> getAllUsers();
	
	public User findBySessionToken(String token);
	
	public Organisation getOrganisationByUserId(String userId);
	
}