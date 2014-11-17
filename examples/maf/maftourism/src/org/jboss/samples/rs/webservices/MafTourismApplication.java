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

import java.util.ArrayList;

import javax.ws.rs.ApplicationPath;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.rhs.tourism.hibernate.BnB;

/*
 * @author mtsietsi
 */
@ApplicationPath("/maftourism")
public class MafTourismApplication  {

	public void addBnB(BnB bnb) throws Exception {
		SessionFactory sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		session.saveOrUpdate(bnb);
		
		session.getTransaction().commit();
		session.close();
		sessionFactory.close();
	}
	
	public void editBnB(BnB bnb) throws Exception {
		SessionFactory sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		session.update(bnb);
		
		session.getTransaction().commit();
		session.close();
		sessionFactory.close();
	}
	
	public ArrayList<BnB> getBnBs() throws Exception {
		SessionFactory sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		Query query = session.createQuery("from BnB");
		ArrayList<BnB> bnbs = (ArrayList<BnB>) query.list();
		
		session.close();
		sessionFactory.close();
		
		return bnbs;
	}
	
	public void deleteBnb(BnB bnb) throws Exception {
		SessionFactory sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		session.delete(bnb);
		
		session.getTransaction().commit();
		session.close();
		sessionFactory.close();
	}
}
