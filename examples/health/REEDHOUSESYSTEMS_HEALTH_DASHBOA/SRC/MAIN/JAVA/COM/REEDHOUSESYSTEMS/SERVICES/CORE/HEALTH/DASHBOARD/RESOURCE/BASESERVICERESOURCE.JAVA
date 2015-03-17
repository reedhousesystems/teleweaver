package com.reedhousesystems.services.core.health.dashboard.resource;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;

import com.reedhousesystems.services.core.health.dashboard.api.ServiceService;

public abstract class BaseServiceResource {


	@Autowired
	protected ServiceService serviceService;

    @Context
    protected UriInfo uriInfo;
	
}