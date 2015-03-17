package com.reedhousesystems.services.core.health.dashboard.resource;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.reedhousesystems.services.core.health.dashboard.api.SurveyService;
import com.reedhousesystems.services.core.health.dashboard.dto.PublishSurveyDTO;
import com.reedhousesystems.services.core.health.dashboard.dto.StatusDTO;
import com.reedhousesystems.services.core.health.dashboard.dto.SurveyDTO;
import com.reedhousesystems.services.core.health.dashboard.dto.TargetDTO;
import com.reedhousesystems.services.core.health.dashboard.dto.UUIDDTO;
import com.reedhousesystems.services.core.health.dashboard.model.Survey;
import com.reedhousesystems.services.core.health.dashboard.model.Target;
import com.reedhousesystems.services.core.health.dashboard.requests.QuestionRequest;
import com.reedhousesystems.services.core.health.dashboard.requests.SurveyRequest;

@Path("/survey")
@Component
@Produces({MediaType.APPLICATION_JSON})
public class SurveyResource {
	
	@Autowired
	private SurveyService surveyService;

    @Context
    protected UriInfo uriInfo;
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	public Response saveSurvey(SurveyRequest request) throws Exception {
		UUIDDTO uuidDTO = null;
		try {
			uuidDTO = surveyService.saveSurvey(request);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return Response.ok().entity(uuidDTO).build();
	}
	
	@PUT
	@Path("{id}")
	@Consumes({MediaType.APPLICATION_JSON})
	public Response updateSurvey (@PathParam("id") String surveyId, SurveyRequest request) throws Exception {
		try {
			surveyService.updateSurvey(surveyId, request);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return Response.noContent().build();
	}
	
	@PUT
	@Path("{id}/status")
	@Consumes({MediaType.APPLICATION_JSON})
	public Response updateSurvey (@PathParam("id") String surveyId, StatusDTO statusDTO) throws Exception {
		try {
			surveyService.updateSurvey(surveyId, statusDTO.getStatus());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return Response.noContent().build();
	}
	
	@DELETE
	@Path("{id}")
	public Response deleteSurvey(@PathParam("id") String surveyId) throws Exception {
		try {
			surveyService.deleteSurvey(surveyId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return Response.noContent().build();
	}
	
	@GET
	@Path("{id}")
	public Response getSurveyById(@PathParam("id") String surveyId) throws Exception {
		SurveyRequest survey = null;
		try {
			survey = surveyService.getSurveyById(surveyId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return Response.ok().entity(survey).build();
	}
	
	@GET
	@Path("{id}/publish")
	public Response getSurveyToPublish(@PathParam("id") String surveyId) throws Exception {
		PublishSurveyDTO survey = null;
		try {
			survey = surveyService.getSurveyToPublish(surveyId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return Response.ok().entity(survey).build();
	}
	
	@GET
	public Response getAllSurveys() throws Exception {
		List<Survey> surveyList = new ArrayList<Survey>();
		try {
			surveyList = surveyService.getAllSurveys();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return Response.ok().entity(surveyList).build();
	}
	
	@PUT
	@Path("{id}/target")
	@Consumes({MediaType.APPLICATION_JSON})
	public Response setSurveyTarget(@PathParam("id") String surveyId, TargetDTO target) throws Exception {
		try {
			surveyService.setSurveyTarget(surveyId, target);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return Response.ok().build();
	}

	@GET
	@Path("{id}/target")
	public Response getSurveyTarget(@PathParam("id") String surveyId) throws Exception {
		Target target = null;
		try {
			target = surveyService.getSurveyTarget(surveyId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (target == null)
			return Response.noContent().build();
		else
			return Response.ok().entity(target).build();
	}
	
	@POST
	@Path("{id}/question")
	@Consumes({MediaType.APPLICATION_JSON})
	public Response saveQuestion(@PathParam("id") String surveyId, QuestionRequest request) throws Exception {
		QuestionRequest question = null;
		try {
			question = surveyService.saveQuestion(surveyId, request);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return Response.ok().entity(question).build();
	}
	
	@PUT
	@Path("{surveyId}/question/{id}")
	@Consumes({MediaType.APPLICATION_JSON})
	public Response updateQuestion(@PathParam("id") long questionId, QuestionRequest question) throws Exception {
		QuestionRequest questionRequest = null;
		try {
			questionRequest = surveyService.updateQuestion(questionId, question);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return Response.ok().entity(questionRequest).build();
	}
	
	@DELETE
	@Path("{surveyId}/question/{id}")
	public Response deleteQuestion(@PathParam("id") long questionId, @PathParam("surveyId") String surveyId) throws Exception {
		try {
			surveyService.deleteQuestion(questionId, surveyId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return Response.ok().build();
	}
	
	@GET
	@Path("question/{questionId}")
	public Response getQuestionById(@PathParam("questionId") long questionId) throws Exception {
		QuestionRequest question = null;
		try {
			question = surveyService.getQuestionById(questionId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return Response.ok().entity(question).build();
	}
	
	@GET
	@Path("{id}/question")
	public Response getQuestionsBySurveyId(@PathParam("id") String surveyId) throws Exception {
		SurveyRequest survey = null;
		try {
			survey = surveyService.getSurveyById(surveyId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return Response.ok().entity(new SurveyDTO(survey)).build();
	}
	
}