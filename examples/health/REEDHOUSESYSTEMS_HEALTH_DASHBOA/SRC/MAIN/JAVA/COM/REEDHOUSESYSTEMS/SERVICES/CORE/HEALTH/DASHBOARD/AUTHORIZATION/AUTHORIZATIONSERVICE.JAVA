package com.reedhousesystems.services.core.health.dashboard.authorization;

import com.reedhousesystems.services.core.health.dashboard.model.ExternalUser;

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
