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

import java.io.InputStream;

import javax.ws.rs.FormParam;

import org.jboss.resteasy.annotations.providers.multipart.PartType;

public class MyMultipartForm {
	@FormParam("imga")
	@PartType("image/png")
	private InputStream imga_input;
	
	@FormParam("imgb")
	@PartType("image/png")
	private InputStream imgb_input;
	
	@FormParam("imgc")
	@PartType("image/png")
	private InputStream imgc_input;
	
	@FormParam("imgd")
	@PartType("image/png")
	private InputStream imgd_input;
	
	@FormParam("imge")
	@PartType("image/png")
	private InputStream imge_input;
	
	public InputStream getImga_input() {
		return imga_input;
	}
	public void setImga_input(InputStream imga_input) {
		this.imga_input = imga_input;
	}
	public InputStream getImgb_input() {
		return imgb_input;
	}
	public void setImgb_input(InputStream imgb_input) {
		this.imgb_input = imgb_input;
	}
	public InputStream getImgc_input() {
		return imgc_input;
	}
	public void setImgc_input(InputStream imgc_input) {
		this.imgc_input = imgc_input;
	}
	public InputStream getImgd_input() {
		return imgd_input;
	}
	public void setImgd_input(InputStream imgd_input) {
		this.imgd_input = imgd_input;
	}
	public InputStream getImge_input() {
		return imge_input;
	}
	public void setImge_input(InputStream imge_input) {
		this.imge_input = imge_input;
	}
}
