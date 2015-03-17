package com.reedhousesystems.services.core.health.dashboard.authorization.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reedhousesystems.services.core.health.dashboard.api.UserDao;
import com.reedhousesystems.services.core.health.dashboard.authorization.AuthorizationRequestContext;
import com.reedhousesystems.services.core.health.dashboard.authorization.AuthorizationService;
import com.reedhousesystems.services.core.health.dashboard.exception.AuthorizationException;
import com.reedhousesystems.services.core.health.dashboard.model.ExternalUser;
import com.reedhousesystems.services.core.health.dashboard.model.SessionToken;
import com.reedhousesystems.services.core.health.dashboard.model.User;

/**
 *
 * Simple authorization service that requires a session token in the Authorization header
 * This is then matched to a user
 *
 */
@Transactional
@Service("sessionTokenAuthorizationService")
public class SessionTokenAuthorizationService implements AuthorizationService {

    /**
     * directly access user objects
     */
	@Autowired
    private UserDao userDao;

    public ExternalUser authorize(AuthorizationRequestContext securityContext) {
        String token = securityContext.getAuthorizationToken();
        ExternalUser externalUser = null;
        if(token == null) {
            return externalUser;
        }
        User user =  userDao.findBySessionToken(token);
        if(user == null) {
            throw new AuthorizationException("Session token not valid");
        }
        for (SessionToken sessionToken : user.getSessions()) {
            if (sessionToken.getToken().equals(token)) {
                sessionToken.setLastUpdated(new Date());
                userDao.saveOrUpdateUser(user);
                externalUser = new ExternalUser(user);
            }
        }
        return externalUser;
    }
}
