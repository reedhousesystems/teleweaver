package com.reedhousesystems.services.core.profile.resource;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;

import com.reedhousesystems.services.core.profile.api.UserService;
import com.reedhousesystems.services.core.profile.api.VerificationTokenService;
import com.reedhousesystems.services.core.profile.config.ApplicationConfig;

public class BaseUserResource {
	
	@Autowired
    protected UserService userService;

    @Autowired
    protected VerificationTokenService verificationTokenService;

    @Autowired
    protected ApplicationConfig config;

    @Context
    protected UriInfo uriInfo;
}