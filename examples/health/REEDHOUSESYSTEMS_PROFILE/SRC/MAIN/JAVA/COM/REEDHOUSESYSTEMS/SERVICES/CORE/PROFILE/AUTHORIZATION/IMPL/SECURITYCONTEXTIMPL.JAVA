package com.reedhousesystems.services.core.profile.authorization.impl;

import java.security.Principal;

import javax.ws.rs.core.SecurityContext;

import com.reedhousesystems.services.core.profile.exception.InvalidAuthorizationHeaderException;
import com.reedhousesystems.services.core.profile.model.ExternalUser;

/**
 * Implementation of {@link javax.ws.rs.core.SecurityContext}
 *
 */
public class SecurityContextImpl implements SecurityContext {

    private final ExternalUser user;

    public SecurityContextImpl(ExternalUser user) {
        this.user = user;
    }

    public Principal getUserPrincipal() {
        return user;
    }

    public boolean isUserInRole(String role) {
        if(user == null) {
            throw new InvalidAuthorizationHeaderException();
        }
        return user.getRole().equalsIgnoreCase(role);
    }

    public boolean isSecure() {
        return false;
    }

    public String getAuthenticationScheme() {
        return SecurityContext.BASIC_AUTH;
    }
}
