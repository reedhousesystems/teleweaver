package com.reedhousesystems.services.core.health.messenger.resource;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.reedhousesystems.services.core.health.messenger.api.SurveyService;
import com.reedhousesystems.services.core.health.messenger.dto.SurveyDTO;
import com.reedhousesystems.services.core.health.messenger.dto.SurveyDTOLite;
import com.reedhousesystems.services.core.health.messenger.model.ExternalUser;
import com.reedhousesystems.services.core.health.messenger.request.SurveyRequest;


@Path("/surveys")
@Component
@Produces({MediaType.APPLICATION_JSON})
public class SurveyResource {
	
	@Autowired
	private SurveyService surveyService;
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON})	
	public Response saveSurvey(SurveyDTO survey) {
		try {
			surveyService.saveSurvey(survey);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return Response.ok().build();
	}
	
	@RolesAllowed({"administrator", "authenticated"})
	@GET
	@Path("{id}")
	public Response getSurveyById(@Context SecurityContext sc, @PathParam("id") String surveyId) {
		ExternalUser userMakingRequest = (ExternalUser)sc.getUserPrincipal();
		SurveyDTOLite survey = null;
		try {
			survey = surveyService.getSurveyById(surveyId, userMakingRequest);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return Response.ok().entity(survey).build();
	}

	@RolesAllowed({"administrator", "authenticated"})
	@GET
	public Response getRunningSurveys(@Context SecurityContext sc) {
		ExternalUser userMakingRequest = (ExternalUser)sc.getUserPrincipal();
		List<SurveyRequest> runningSurveys = new ArrayList<SurveyRequest>();
		try {
			runningSurveys = surveyService.getRunningSurveys(userMakingRequest);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return Response.ok().entity(runningSurveys).build();
	}

}