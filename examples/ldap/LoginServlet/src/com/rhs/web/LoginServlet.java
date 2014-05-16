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

package com.rhs.web;

import java.io.IOException;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author mtsietsi
 */

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}
	
	private void processRequest(HttpServletRequest request, HttpServletResponse response) {
		final String SUCCESS = "LoginSuccess.jsp";
		final String FAILURE = "LoginFail.jsp";
		String strUrl = "LoginPage.jsp";
		
		String strUsername = request.getParameter("uname");
		String strPassword = request.getParameter("passwd");
		String strGroup = request.getParameter("group");
		String strDn;
		
		if(strGroup.compareTo("Admin") == 0) strDn = "cn="+ strUsername + ",dc=rhs,dc=za";
		else strDn = "uid="+ strUsername + ",ou=" + strGroup +",dc=rhs,dc=za";

		Hashtable<String, String> env = new Hashtable<String,String>();

		boolean boolResult = false;
		
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, "ldap://g06t6632-4.ict.ru.ac.za:389/");
		
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, strDn);
		env.put(Context.SECURITY_CREDENTIALS, strPassword);
				
		try {
			DirContext ctx = new InitialDirContext(env);
			boolResult = ctx != null;
			
			if(ctx != null)		ctx.close();
			
			if(boolResult){
				System.out.print("Success");
				strUrl = SUCCESS;
			}
			
			RequestDispatcher rd = request.getRequestDispatcher(strUrl);
			rd.forward(request, response);

		} 
		
		catch (NamingException e) {
			RequestDispatcher rd = request.getRequestDispatcher(FAILURE);
			try {
				rd.forward(request, response);
			} catch (ServletException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		catch (ServletException e) {
			e.printStackTrace();
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
