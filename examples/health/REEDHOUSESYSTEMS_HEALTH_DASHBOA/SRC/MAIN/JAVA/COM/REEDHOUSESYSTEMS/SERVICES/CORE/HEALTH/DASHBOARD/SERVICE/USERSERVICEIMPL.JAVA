package com.reedhousesystems.services.core.health.dashboard.service;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.reedhousesystems.services.core.health.dashboard.api.OrganisationDao;
import com.reedhousesystems.services.core.health.dashboard.api.UserDao;
import com.reedhousesystems.services.core.health.dashboard.api.UserService;
import com.reedhousesystems.services.core.health.dashboard.exception.AuthenticationException;
import com.reedhousesystems.services.core.health.dashboard.exception.AuthorizationException;
import com.reedhousesystems.services.core.health.dashboard.exception.DuplicateUserException;
import com.reedhousesystems.services.core.health.dashboard.exception.WrongPasswordException;
import com.reedhousesystems.services.core.health.dashboard.model.AuthenticatedUserToken;
import com.reedhousesystems.services.core.health.dashboard.model.Contact;
import com.reedhousesystems.services.core.health.dashboard.model.ExternalUser;
import com.reedhousesystems.services.core.health.dashboard.model.Organisation;
import com.reedhousesystems.services.core.health.dashboard.model.Role;
import com.reedhousesystems.services.core.health.dashboard.model.User;
import com.reedhousesystems.services.core.health.dashboard.requests.CreateUserRequest;
import com.reedhousesystems.services.core.health.dashboard.requests.LoginRequest;
import com.reedhousesystems.services.core.health.dashboard.requests.PasswordRequest;
import com.reedhousesystems.services.core.health.dashboard.requests.UpdateContactsRequest;
import com.reedhousesystems.services.core.health.dashboard.requests.UpdateStatusRequest;
import com.reedhousesystems.services.core.health.dashboard.requests.UpdateUserRequest;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;
	
	@Resource
	private OrganisationDao organisationDao;

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    /**
     * {@inheritDoc}
     *
     * This method creates a User with the given Role. A check is made to see if the username already exists and a duplication
     * check is made on the email address if it is present in the request.
     * <P></P>
     * The password is hashed and a SessionToken generated for subsequent authorization of role-protected requests.
     *
     */
    
    public void createUser(CreateUserRequest request) {
        User searchedForUser = userDao.getUserByUserName(request.getUser().getUserName());
        if (searchedForUser != null) {
            throw new DuplicateUserException();
        }
        Role role = request.getUser().getRole().equals("authenticated") ? Role.authenticated : Role.administrator;
        User newUser = createNewUser(request, role);
        userDao.saveOrUpdateUser(newUser);
    }

    public AuthenticatedUserToken createUser(Role role) {
        User user = new User();
        user.setRole(role);
        AuthenticatedUserToken token = new AuthenticatedUserToken(user.getUuid().toString(), user.addSessionToken().getToken(), user.getFirstName(), user.getLastName());
        userDao.saveOrUpdateUser(user);
        return token;
    }

    public ExternalUser updateUser(String uuid, UpdateUserRequest request) {
    	Assert.notNull(uuid);
        Assert.notNull(request);
    	User userToUpdate = userDao.getUserByUUId(uuid);
		if (request.getFirstName() != null)
			userToUpdate.setFirstName(request.getFirstName());
		if (request.getLastName() != null)
			userToUpdate.setLastName(request.getLastName());
		if (!request.getUserName().equals(userToUpdate.getUserName())) {
			User user = userDao.getUserByUserName(request.getUserName());
	    	if (user != null)
	    		throw new DuplicateUserException();
			userToUpdate.setUserName(request.getUserName());
		}
		Role role = request.getRole().equals("administrator") ? Role.administrator : Role.authenticated;
		userToUpdate.setRole(role);
		if (request.getOrganisation() != null) {
			Organisation organisation = null;
			try {
				organisation = organisationDao.getOrganisationById(request.getOrganisation());
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			userToUpdate.setOrganisation(organisation);
		}
        return new ExternalUser(userToUpdate);
    }
    
    public void updateUser(String uuid, UpdateContactsRequest request) {
        Assert.notNull(uuid);
        Assert.notNull(request);
    	User userToUpdate = userDao.getUserByUUId(uuid);
		if (request.getEmailAddress() != null) userToUpdate.getContact().setEmailAddress(request.getEmailAddress());
		if (request.getPhoneNumber() != null) userToUpdate.getContact().setPhoneNumber(request.getPhoneNumber());
    }

	public void updateUser(String uuid, UpdateStatusRequest request) {
        Assert.notNull(uuid);
        Assert.notNull(request);
        User userToUpdate = userDao.getUserByUUId(uuid);
		userToUpdate.setStatus(request.getStatus());
	}
    
    public void deleteUser(ExternalUser userMakingRequest, String uuid) {
        Assert.notNull(userMakingRequest);
        Assert.notNull(uuid);
        User userToDelete = userDao.getUserByUUId(uuid);
        if (userMakingRequest.getRole().equalsIgnoreCase(Role.administrator.toString()) && (userToDelete.hasRole(Role.anonymous) || userToDelete.hasRole(Role.authenticated))) {
            userDao.deleteUser(userToDelete);
        } else {
            throw new AuthorizationException("User cannot be deleted. Only users with anonymous or authenticated role can be deleted.");
        }
    }
    
    /**
     * Allow user to get their own profile or a user with administrator role to get any profile
     *
     * @param requestingUser
     * @param userIdentifier
     * @return 
     * @return user
     */
    public ExternalUser getUser(ExternalUser requestingUser, String uuid) {
        Assert.notNull(requestingUser);
        Assert.notNull(uuid);
        User user = userDao.getUserByUUId(uuid);
        if(!requestingUser.getId().equals(user.getUuid().toString()) && !requestingUser.getRole().equalsIgnoreCase(Role.administrator.toString()))  {
           throw new AuthorizationException("User not authorized to load profile");
        }
        return new ExternalUser(user);
    }

    public Contact getContacts(ExternalUser requestingUser, String uuid) {
    	Assert.notNull(requestingUser);
    	Assert.notNull(uuid);
    	User user = userDao.getUserByUUId(uuid);
        if(!requestingUser.getId().equals(user.getUuid().toString()) && !requestingUser.getRole().equalsIgnoreCase(Role.administrator.toString()))  {
            throw new AuthorizationException("User not authorized to load profile");
         }
    	return new Contact(user);
    }
    
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
    public AuthenticatedUserToken login(LoginRequest request) {
        User user = userDao.getUserByUserName(request.getUsername());
        if (user == null) {
            throw new AuthenticationException();
        }
        if (!user.getRole().toString().equals(request.getRole())) {
        	if (!user.getRole().toString().equals("administrator"))
        	throw new AuthorizationException("Only System Administrators are allowed to log on this page. Please login to an appropriate page");
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
        } else {
    		int loginAttempts = user.getLoginAttempts() + 1;
        	if (loginAttempts == 3) user.setStatus(2);
        	user.setLoginAttempts(loginAttempts);
            throw new AuthenticationException();
        }
        return new AuthenticatedUserToken(user.getUuid().toString(), user.addSessionToken().getToken(), user.getFirstName(), user.getLastName());
    }

    /*public void resetPassword(String userId, PasswordRequest passwordRequest) {
        Assert.notNull(userId);
        Assert.notNull(passwordRequest);
        User userToUpdate = loadUser(uuid);
		try {
			userToUpdate.setPassword(userToUpdate.hashPassword(request.getPassword()));
	    }  catch (Exception e) {
	        throw new AuthenticationException();
	    }
        userDao.updateUser(userId, passwordRequest);
    }*/

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
		try {
			Organisation organisation = organisationDao.getOrganisationById(request.getUser().getOrganisation());
			userToSave.setOrganisation(organisation);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
        userToSave.setRole(role);
        return userToSave;
    }

}
