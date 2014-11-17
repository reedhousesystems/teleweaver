/****************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one   *
 * or more contributor license agreements.  See the NOTICE file *
 * distributed with this work for additional information        *
 * regarding copyright ownership.  The ASF licenses this file   *
 * to you under the Apache License, Version 2.0 (the            *
 * "License"); you may not use this file except in compliance   *
 * with the License.  You may obtain a copy of the License at   *
 *                                                              *
 *   http://www.apache.org/licenses/LICENSE-2.0                 *
 *                                                              *
 * Unless required by applicable law or agreed to in writing,   *
 * software distributed under the License is distributed on an  *
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY       *
 * KIND, either express or implied.  See the License for the    *
 * specific language governing permissions and limitations      *
 * under the License.                                           *
 ****************************************************************/

package org.jboss.samples.rs.webservices;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.rhs.tourism.hibernate.BnB;

/*
 * @author mtsietsi
 */
@Path("/v1.0")
public class MafTourismResource {

	@Inject
	MafTourismApplication service;
	
	@POST
	@Path("/bnb")
	@Consumes({ "application/json" })
	@Produces({ "application/json" })
	public Response addBnB(BnB bnb) {
		try {
			int bnbId = bnb.getBnbId();
			bnb.setBnbId(new Random().nextInt());
			if(bnb.getBnbId() < 1) bnb.setBnbId(bnb.getBnbId() * -1);
			
			service.addBnB(bnb);
			BnBResponse obj = new BnBResponse();
			
			obj.setOldId(bnbId);
			obj.setId(bnb.getBnbId());
			return Response.status(Response.Status.OK).entity(obj).build();
		} catch(IOException jpe) {
			System.out.println("Message " + jpe.getMessage());
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity("{\"message\": \"something went awry\"}").build();
		} catch(Exception e) {
			System.out.println("Message " + e.getMessage());
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity("{\"message\": \"something went awry\"}").build();
		}
	}
	
	@POST
	@Path("/bnbs")
	@Consumes({ "application/json" })
	@Produces({ "application/json" })
	public Response addBnBsPOST(ArrayList<BnB> bnbs) {
		try {
			ArrayList<BnBResponse> objs = new ArrayList<BnBResponse>();
			
			for(int i = 0; i < bnbs.size(); i++) {
				BnB bnb = bnbs.get(i);
				int bnbId = bnb.getBnbId();
				bnb.setBnbId(new Random().nextInt());
				if(bnb.getBnbId() < 1) bnb.setBnbId(bnb.getBnbId() * -1);
				service.addBnB(bnb);
				
				BnBResponse obj = new BnBResponse();
				obj.setOldId(bnbId);
				obj.setId(bnb.getBnbId());
				objs.add(obj);
			}
			return Response.status(Response.Status.OK).entity(objs).build();
		} catch(IOException jpe) {
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity("{\"message\": \"something went awry\"}").build();
		} catch(Exception e) {
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity("{\"message\": \"something went awry\"}").build();
		}
	}
	
	@PUT
	@Path("/bnb")
	@Consumes({ "application/json" })
	@Produces({ "application/json" })
	public Response editBnB(BnB bnb) {
		try{
			service.editBnB(bnb);
			return Response.status(Response.Status.OK).build();
		} catch(IOException jpe) {
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity("{\"message\": \"something went awry\"}").build();
		} catch(Exception e) {
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity("{\"message\": \"something went awry\"}").build();
		}
	}
	
	@PUT
	@Path("/bnbs")
	@Consumes({ "application/json" })
	@Produces({ "application/json" })
	public Response addBnBsPUT(ArrayList<BnB> bnbs) {
		try {
			ArrayList<BnBResponse> objs = new ArrayList<BnBResponse>();
			
			for(int i = 0; i < bnbs.size(); i++) {
				BnB bnb = bnbs.get(i);
				int bnbId = bnb.getBnbId();
				bnb.setBnbId(new Random().nextInt());
				if(bnb.getBnbId() < 1) bnb.setBnbId(bnb.getBnbId() * -1);
				service.addBnB(bnb);
				
				BnBResponse obj = new BnBResponse();
				obj.setOldId(bnbId);
				obj.setId(bnb.getBnbId());
				objs.add(obj);
			}
			return Response.status(Response.Status.OK).entity(objs).build();
		} catch(IOException jpe) {
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity("{\"message\": \"something went awry\"}").build();
		} catch(Exception e) {
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity("{\"message\": \"something went awry\"}").build();
		}
	}
	
	@GET
	@Path("/bnbs")
	@Produces({ "application/json" })
	public Response getBnBs() {
		try {
			ArrayList<BnB> bnbs = new ArrayList<BnB>();
			bnbs = (ArrayList<BnB>) service.getBnBs();
			return Response.status(Response.Status.OK).entity(bnbs).build();
		} catch(IOException jpe) {
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity("{\"message\": \"something went awry\"}").build();
		} catch(Exception e) {
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity("{\"message\": \"something went awry\"}").build();
		}
	}
	
	@DELETE
	@Path("/bnb")
	@Consumes({ "application/json" })
	public Response deleteProduct(BnB bnb) {
		try{ 
			service.deleteBnb(bnb);
			return Response.status(Response.Status.OK).build();
		} catch(IOException jpe) {
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity("{\"message\": \"something went awry\"}").build();
		} catch (Exception e) {
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity("{\"message\": \"something went awry\"}").build();
		}
	}
}
