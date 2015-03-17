package com.reedhousesystems.services.core.profile.service;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.reedhousesystems.services.core.profile.api.LocationDao;
import com.reedhousesystems.services.core.profile.api.UserDao;
import com.reedhousesystems.services.core.profile.api.UserService;
import com.reedhousesystems.services.core.profile.dto.DemographicDTO;
import com.reedhousesystems.services.core.profile.exception.AuthenticationException;
import com.reedhousesystems.services.core.profile.exception.AuthorizationException;
import com.reedhousesystems.services.core.profile.exception.DuplicateUserException;
import com.reedhousesystems.services.core.profile.exception.WrongPasswordException;
import com.reedhousesystems.services.core.profile.model.AuthenticatedUserToken;
import com.reedhousesystems.services.core.profile.model.Contact;
import com.reedhousesystems.services.core.profile.model.Demographic;
import com.reedhousesystems.services.core.profile.model.ExternalUser;
import com.reedhousesystems.services.core.profile.model.Location;
import com.reedhousesystems.services.core.profile.model.Role;
import com.reedhousesystems.services.core.profile.model.User;
import com.reedhousesystems.services.core.profile.request.ContactRequest;
import com.reedhousesystems.services.core.profile.request.CreateUserRequest;
import com.reedhousesystems.services.core.profile.request.LoginRequest;
import com.reedhousesystems.services.core.profile.request.PasswordRequest;
import com.reedhousesystems.services.core.profile.request.StatusRequest;
import com.reedhousesystems.services.core.profile.request.UserRequest;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;
	@Autowired
	private LocationDao locationDao;

    /**
     * {@inheritDoc}
     *
     * This method creates a User with the given Role. A check is made to see if the username already exists and a duplication
     * check is made on the email address if it is present in the request.
     * <P></P>
     * The password is hashed and a SessionToken generated for subsequent authorization of role-protected requests.
     *
     */
    
    @Transactional
    public AuthenticatedUserToken createUser(CreateUserRequest request) {
        User searchedForUser = userDao.getUserByUserName(request.getUser().getUserName());
        if (searchedForUser != null) {
            throw new DuplicateUserException();
        }
        Role role = request.getUser().getRole().equals("authenticated") ? Role.authenticated : Role.administrator;
        User newUser = createNewUser(request, role);
        AuthenticatedUserToken token = new AuthenticatedUserToken(newUser.getUuid().toString(), newUser.getFirstName(), newUser.getLastName(), newUser.addSessionToken().getToken(), false);
        userDao.saveOrUpdateUser(newUser);
        return token;
    }

    @Transactional
    public AuthenticatedUserToken createUser(Role role) {
        User user = new User();
        user.setRole(role);
        AuthenticatedUserToken token = new AuthenticatedUserToken(user.getUuid().toString(), user.getFirstName(), user.getLastName(), user.addSessionToken().getToken(), false);
        userDao.saveOrUpdateUser(user);
        return token;
    }

    @Transactional
    public void updateUser(String uuid, UserRequest request) {
    	Assert.notNull(uuid);
        User userToUpdate = userDao.getUserByUUId(uuid);
        if (request.getFirstName() != null) userToUpdate.setFirstName(request.getFirstName());
        if (request.getLastName() != null) userToUpdate.setLastName(request.getLastName());
        if (request.getRole() != null) {
        	Role role = request.getRole().equals("administrator") ? Role.administrator : Role.authenticated;
        	userToUpdate.setRole(role);
        }
        if (!request.getUserName().equals(userToUpdate.getUserName())) {
        	User userWithNewUserName = userDao.getUserByUserName(request.getUserName());
        	if (userWithNewUserName != null) throw new DuplicateUserException();
        }
    }
    
    @Transactional
    public void updateUser(String uuid, DemographicDTO demographicDTO) {
    	Assert.notNull(uuid);
        User userToUpdate = userDao.getUserByUUId(uuid);
        Location location = locationDao.getLocation(demographicDTO.getLocationDTO());
        Demographic demographic = new Demographic(demographicDTO);
        demographic.setLocation(location);
        Demographic savedDemographics = userDao.saveDemographics(demographic);
        userToUpdate.setDemographics(savedDemographics);
    }
    
    @Transactional
    public void updateUser(String uuid, ContactRequest request) {
    	Assert.notNull(uuid);
        User userToUpdate = userDao.getUserByUUId(uuid);
		if (request.getEmailAddress() != null) userToUpdate.getContact().setEmailAddress(request.getEmailAddress());
		if (request.getPhoneNumber() != null) userToUpdate.getContact().setPhoneNumber(request.getPhoneNumber());
    }

	public void changePassword(String uuid, PasswordRequest request) {
    	Assert.notNull(uuid);
        User userToUpdate = userDao.getUserByUUId(uuid);
		try {
		userToUpdate.setPassword(userToUpdate.hashPassword(request.getPassword()));
	    }  catch (Exception e) {
	        throw new AuthenticationException();
	    }
	}

	public void updateUser(String uuid, StatusRequest request) {
    	Assert.notNull(uuid);
    	Assert.notNull(request);
        User userToUpdate = userDao.getUserByUUId(uuid);
        userToUpdate.setStatus(request.getStatus());
	}
    
    @Transactional
    public void deleteUser(String uuid) {
        Assert.notNull(uuid);
        User userToDelete = userDao.getUserByUUId(uuid);
        userDao.deleteUser(userToDelete);
    }
    
    /**
     * Allow user to get their own profile or a user with administrator role to get any profile
     *
     * @param requestingUser
     * @param userIdentifier
     * @return 
     * @return user
     */
    @Transactional
    public ExternalUser getUser(ExternalUser requestingUser, String uuid) {
        Assert.notNull(requestingUser);
        Assert.notNull(uuid);
        User user = userDao.getUserByUUId(uuid);
        if(!requestingUser.getId().equals(user.getUuid().toString()) && !requestingUser.getRole().equalsIgnoreCase(Role.administrator.toString()))  {
           throw new AuthorizationException("User not authorized to load profile");
        }
        return new ExternalUser(user);
    }

    @Transactional
    public Contact getContacts(ExternalUser requestingUser, String uuid) {
    	Assert.notNull(requestingUser);
    	Assert.notNull(uuid);
    	User user = userDao.getUserByUUId(uuid);
        if(!requestingUser.getId().equals(user.getUuid().toString()) && !requestingUser.getRole().equalsIgnoreCase(Role.administrator.toString()))  {
            throw new AuthorizationException("User not authorized to load profile");
         }
    	return new Contact(user);
    }
    
    @Transactional
    public Demographic getDemographics(ExternalUser requestingUser, String uuid) {
    	Assert.notNull(requestingUser);
    	Assert.notNull(uuid);
    	User user = userDao.getUserByUUId(uuid);
        if(!requestingUser.getId().equals(user.getUuid().toString()) && !requestingUser.getRole().equalsIgnoreCase(Role.administrator.toString()))  {
            throw new AuthorizationException("User not authorized to load profile");
         }
    	return user.getDemographics();
    }
    
    @Transactional
    public List<ExternalUser> getAllUsers(ExternalUser requestingUser) {
    	Assert.notNull(requestingUser);
    	if (!requestingUser.getRole().equalsIgnoreCase(Role.administrator.toString())) {
    		throw new AuthorizationException("User not allowed to access users");
    	}
    	List<User> userList = userDao.getAllUsers();
    	List<ExternalUser> userListToReturn = new ArrayList<ExternalUser>();
    	for (User user : userList) {
    		ExternalUser externalUser = new ExternalUser(user);
    		userListToReturn.add(externalUser);
    	}
    	return userListToReturn;
    }

    @Transactional
    public Integer deleteExpiredSessions(int timeSinceLastUpdatedInMinutes) {
        DateTime date = new DateTime();
        date = date.minusMinutes(timeSinceLastUpdatedInMinutes);
        List<User> expiredUserSessions = userDao.getUsersWithExpiredTokens(date.toDate());
        int count = expiredUserSessions.size();
        for(User user : expiredUserSessions) {
            user.removeExpiredSessions(date.toDate());
        }
        if(count > 0) {
        	for (User user : expiredUserSessions) {
        		userDao.saveOrUpdateUser(user);
        	}
        }
        return count;
    }

    /**
     * {@inheritDoc}
     *
     *  Login supports authentication against an username attribute.
     *  If a User is retrieved that matches, the password in the request is hashed
     *  and compared to the persisted password for the User account.
     */
    @Transactional
    public AuthenticatedUserToken login(LoginRequest request) {
        User user = null;
        user = userDao.getUserByUserName(request.getUsername());
        if (user == null) {
            throw new AuthenticationException();
        }
        switch (user.getStatus()) {
        case 2:
        	throw new AuthorizationException("User account is locked. Please contact the System Administrator");
		case 3:
			throw new AuthorizationException("User account is inactive. Please contact the system administrator");
        }
        String hashedPassword = null;
        try {
            hashedPassword = user.hashPassword(request.getPassword());
        } catch (Exception e) {
            throw new AuthenticationException();
        }
        if (hashedPassword.equals(user.getPassword())) {
        	user.setStatus(request.getStatus());
        	user.setLoginAttempts(0);
        } else {
        	int loginAttempts = user.getLoginAttempts() + 1;
        	if (loginAttempts == 3)
        		user.setStatus(2);
        	user.setLoginAttempts(loginAttempts);
            throw new AuthenticationException();
        }
        boolean profileComplete = false;
        if (user.getDemographics() != null)
        	profileComplete = isProfileComplete(user.getDemographics());
        return new AuthenticatedUserToken(user.getUuid().toString(), user.getFirstName(), user.getLastName(), user.addSessionToken().getToken(), profileComplete);
    }

    public void resetPassword(ExternalUser userMakingRequest, String userId, PasswordRequest request) {
        Assert.notNull(userId);
        Assert.notNull(request);
        User user = userDao.getUserByUUId(userId);
        if (user == null) throw new AuthenticationException();
        if (!userMakingRequest.getUserName().equals(user.getUserName())) {
        	if (!userMakingRequest.getRole().equalsIgnoreCase("administrator")) {
        		throw new AuthorizationException("You are not allowed to change other users' passwords");
        	}
        } else {
            String hashedPassword = null;
            try {
            	hashedPassword = user.hashPassword(request.getCurrentPassword());
            } catch (Exception e) {
                throw new AuthenticationException();
            }
            if (!user.getPassword().equals(hashedPassword)) throw new WrongPasswordException();
        }
        String newHashedPassword = null;
        try {
        	newHashedPassword = user.hashPassword(request.getPassword());
        } catch (Exception e) {
            throw new AuthenticationException();
        }
        user.setPassword(newHashedPassword);
    }

    private User createNewUser(CreateUserRequest request, Role role) {
        User userToSave = new User(request.getUser());
        try {
            userToSave.setPassword(userToSave.hashPassword(request.getPassword().getPassword()));
        }  catch (Exception e) {
            throw new AuthenticationException();
        }
        userToSave.setRole(role);
        return userToSave;
    }
    
    private boolean isProfileComplete(Demographic profile) {
    	boolean profileIsComplete = false;
    	if (profile.getDateOfBirth() != null && profile.getGender() != null &&
    		profile.getMaritalStatus() != null && profile.getSexOrientation() != null &&
    		profile.getLanguage() != null && profile.getLocation().getProvince() != null &&
    		profile.getLocation().getDistrict() != null && profile.getLocation().getMunicipality() != null &&
    		profile.getLocation().getVillage() != null)
    			profileIsComplete = true;
    	return profileIsComplete;    	
    }

}
