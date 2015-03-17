package com.reedhousesystems.services.core.profile.authorization;

import com.reedhousesystems.services.core.profile.model.ExternalUser;

/**
 *
 * @author: C. Gremu
 */
public interface AuthorizationService {

    /**
     * Given an AuthorizationRequestContext validate and authorize a User
     *
     * @param authorizationRequestContext the context required to authorize a user for a particular request
     * @return ExternalUser
     */
    public ExternalUser authorize(AuthorizationRequestContext authorizationRequestContext);
}
