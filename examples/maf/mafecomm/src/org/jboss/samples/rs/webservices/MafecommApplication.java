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
import java.util.List;

import javax.ws.rs.ApplicationPath;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.rhs.ecommerce.hibernate.Order;
import org.rhs.ecommerce.hibernate.Product;

/*
 * @author mtsietsi
 */
@ApplicationPath("/mafecomm")
public class MafecommApplication {

	public void addOrder(Order order) throws Exception {
		SessionFactory sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		session.save(order);
		
		session.getTransaction().commit();
		session.close();
		sessionFactory.close();
	}
	
	public void editOrder(Order order) throws Exception {
		SessionFactory sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		session.update(order);
		
		session.getTransaction().commit();
		session.close();
		sessionFactory.close();
	}
	
	public ArrayList<Order> getOrders() throws Exception {
		SessionFactory sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		Query query = session.createQuery("from Order");
		ArrayList<Order> orders = (ArrayList<Order>) query.list();
		
		session.close();
		sessionFactory.close();
		
		return orders;
	}
	
	public void addProduct(Product product) throws Exception {
		SessionFactory sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		session.save(product);
		
		session.getTransaction().commit();
		session.close();
		sessionFactory.close();
	}
	
	public void editProduct(Product product) throws Exception {
		SessionFactory sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		session.update(product);
		
		session.getTransaction().commit();
		session.close();
		sessionFactory.close();
	}
	
	public Product getProduct(int productId) throws Exception {
		Product product;
		
		SessionFactory sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		Query query = session.createQuery("from Product P WHERE P.productId = :product_Id");
		query.setParameter("product_Id", productId);
		List<Product> results = (List<Product>) query.list();
		product = results.get(0);
		
		session.close();
		sessionFactory.close();
		
		return product;
	}
	
	public ArrayList<Product> getProducts() throws Exception {
		SessionFactory sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		Query query = session.createQuery("from Product");
		ArrayList<Product> products = (ArrayList<Product>) query.list();
		
		session.close();
		sessionFactory.close();
		
		return products;
	}
	
	public void deleteProduct(Product product) throws Exception {
		SessionFactory sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		session.delete(product);
		
		session.getTransaction().commit();
		session.close();
		sessionFactory.close();
	}
	
	public void deleteProductById(int productId) throws Exception {
		SessionFactory sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		Query query = session.createQuery("DELETE FROM Product WHERE productId = :productId");
		query.setParameter("productId", productId);
		
		
		query.executeUpdate();
		
		session.getTransaction().commit();
		session.close();
		sessionFactory.close();
	}
}
