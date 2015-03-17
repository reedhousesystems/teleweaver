package com.reedhousesystems.services.core.profile.resource;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;

import com.reedhousesystems.services.core.profile.api.UserService;

public class BaseResource {
	
	@Autowired
    protected UserService userService;

    @Context
    protected UriInfo uriInfo;
    
}