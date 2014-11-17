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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Random;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.rhs.ecommerce.hibernate.Order;
import org.rhs.ecommerce.hibernate.Product;

/* 
 * @author mtsietsi
 */
@Path("/v1.1")
public class MafecommResource2 {
	@Inject
	MafecommApplication service;
	
	@GET
	@Path("getNotifierMessage")
	@Produces({ "text/plain" })
	public Response getMessage() {
		return Response.status(Response.Status.OK).entity(new String("Welcome to Teleweaver")).build();
	}
	
	@POST
	@Path("/order")
	@Consumes({ "application/json" })
	@Produces({ "application/json" })
	public Response addOrder(Order order){
		try {
			int oldId = order.getId();
			order.setOrderId(new Random().nextInt());
			if(order.getOrderId() < 1) order.setOrderId(order.getOrderId() * -1);
			
			OrderResponse obj = new OrderResponse();
			obj.setOldId(oldId);
			obj.setOrderId(order.getOrderId());
			obj.setStatus(order.getStatus());
			obj.setProductId(order.getProductId());
			obj.setQuantity(order.getQuantity());
			
			service.addOrder(order);
			return Response.status(Response.Status.OK).entity(obj).build();
		} catch(IOException jpe) {
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity("{\"message\": \""+ jpe.getMessage() +"\"}").build();
		} catch (Exception e) {
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity("{\"message\": \""+ e.getMessage() +"\"}").build();
		}
	}
	
	@PUT
	@Path("/order")
	@Consumes({ "application/json" })
	@Produces({ "application/json" })
	public Response editOrder(Order order) {
		try{
			service.editOrder(order);
			
			OrderResponse obj = new OrderResponse();
			obj.setOrderId(order.getOrderId());
			obj.setStatus(order.getStatus());
			
			return Response.status(Response.Status.OK).entity(obj).build();
		} catch(IOException jpe) {
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity("{\"message\": \""+ jpe.getMessage() +"\"}").build();
		} catch (Exception e) {
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity("{\"message\": \""+ e.getMessage() +"\"}").build();
		}
	}
	
	@GET
	@Path("/orders")
	@Produces({ "application/json" })
	public Response getOrders(){
		try {
			ArrayList<Order> orders = service.getOrders();
			ArrayList<OrderResponse> responses = new ArrayList<OrderResponse>();
			
			for(int i = 0; i < orders.size(); i++) {
				Order order = orders.get(i);
				
				OrderResponse response = new OrderResponse();
				response.setOrderId(order.getOrderId());
				response.setProductId(order.getProductId());
				response.setQuantity(order.getQuantity());
				response.setStatus(order.getStatus());
				
				responses.add(response);
			}
			return Response.status(Response.Status.OK).entity(responses).build();
		} catch(IOException jpe) {
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity("{\"message\": \""+ jpe.getMessage() +"\"}").build();
		} catch (Exception e) {
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity("{\"message\": \""+ e.getMessage() +"\"}").build();
		}
	}
	
	@POST
	@Path("/product")
	@Consumes({ "application/json" })
	@Produces({ "application/json" })
	public Response addProduct(Product product) {
		try {
			int oldId = product.getId();
			product.setProductId(new Random().nextInt());
			if(product.getProductId() < 1) product.setProductId(product.getProductId() * -1);
			
			service.addProduct(product);
			ProductResponse obj = new ProductResponse();
			
			obj.setOldId(oldId);
			obj.setProductId(product.getProductId());
			return Response.status(Response.Status.OK).entity(obj).build();
		} catch(IOException jpe) {
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity("{\"message\": \"\""+jpe.getMessage()+"}").build();
		} catch(Exception e) {
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity("{\"message\": \"\""+e.getMessage()+"}").build();
		}
	}
	
	@POST
	@Path("/product/{id}")
	@Consumes({ "application/json" })
	@Produces({ "application/json" })
	public Response addProduct(@PathParam("id") int tempId, Product product) {
		try {
			int oldId = tempId;
			product.setProductId(new Random().nextInt());
			if(product.getProductId() < 1) product.setProductId(product.getProductId() * -1);
			
			service.addProduct(product);
			ProductResponse obj = new ProductResponse();
			
			obj.setOldId(oldId);
			obj.setProductId(product.getProductId());
			return Response.status(Response.Status.OK).entity(obj).build();
		} catch(IOException jpe) {
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity("{\"message\": \"\""+jpe.getMessage()+"}").build();
		} catch(Exception e) {
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity("{\"message\": \"\""+e.getMessage()+"}").build();
		}
	}
	
	@POST
	@Path("/products")
	@Consumes({ "application/json" })
	@Produces({ "application/json" })
	public Response addProductsPOST(ArrayList<Product> products) {
		try {
			ArrayList<ProductResponse> objs = new ArrayList<ProductResponse>();
			
			for(int i = 0; i < products.size(); i++) {
				Product product = products.get(i);
				int oldId = product.getId();
				product.setProductId(new Random().nextInt());
				if(product.getProductId() < 1) product.setProductId(product.getProductId() * -1);
				service.addProduct(product);
				
				ProductResponse obj = new ProductResponse();
				obj.setOldId(oldId);
				obj.setProductId(product.getProductId());
				objs.add(obj);
			}
			return Response.status(Response.Status.OK).entity(objs).build();
		} catch(IOException jpe) {
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity("{\"message\": \""+ jpe.getMessage() +"\"}").build();
		} catch (Exception e) {
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity("{\"message\": \""+ e.getMessage() +"\"}").build();
		}
	}
	
	@PUT
	@Path("/product")
	@Consumes({ "application/json" })
	@Produces({ "application/json" })
	public Response editProduct(Product product) {
		try {
			service.editProduct(product);
			return Response.status(Response.Status.OK).build();
		} catch(IOException jpe) {
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity("{\"message\": \""+ jpe.getMessage() +"\"}").build();
		} catch (Exception e) {
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity("{\"message\": \""+ e.getMessage() +"\"}").build();
		}
	}
	
	@PUT
	@Path("/product/{productId}")
	@Consumes({ "application/json" })
	@Produces({ "application/json" })
	public Response editProduct(@PathParam("productId") int productId, Product product) {
		try {
			service.editProduct(product);
			return Response.status(Response.Status.OK).build();
		} catch(IOException jpe) {
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity("{\"message\": \""+ jpe.getMessage() +"\"}").build();
		} catch (Exception e) {
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity("{\"message\": \""+ e.getMessage() +"\"}").build();
		}
	}
	
	@PUT
	@Path("/products")
	@Consumes({ "application/json" })
	@Produces({ "application/json" })
	public Response addProductsPUT(ArrayList<Product> products) {
		try {
			ArrayList<ProductResponse> objs = new ArrayList<ProductResponse>();
			
			for(int i = 0; i < products.size(); i++) {
				Product product = products.get(i);
				int productId = product.getProductId();
				product.setProductId(new Random().nextInt());
				if(product.getProductId() < 1) product.setProductId(product.getProductId() * -1);
				service.addProduct(product);
				
				ProductResponse obj = new ProductResponse();
				obj.setOldId(productId);
				obj.setProductId(product.getProductId());
				objs.add(obj);
			}
			return Response.status(Response.Status.OK).entity(objs).build();
		} catch(IOException jpe) {
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity("{\"message\": \""+ jpe.getMessage() +"\"}").build();
		} catch (Exception e) {
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity("{\"message\": \""+ e.getMessage() +"\"}").build();
		}
	}
	
	@GET
	@Path("/products")
	@Produces({ "application/json" })
	public Response getProducts() {
		try {
			ArrayList<Product> products = new ArrayList<Product>();
			products = (ArrayList<Product>) service.getProducts();
			return Response.status(Response.Status.OK).entity(products).build();
		} catch(IOException jpe) {
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity("{\"message\": \""+ jpe.getMessage() +"\"}").build();
		} catch(Exception e) {
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity("{\"message\": \""+ e.getMessage() +"\"}").build();
		}
	}
	
	@GET
	@Path("/product/{productId}")
	@Produces({ "application/json" })
	public Response getProduct(@PathParam("productId") int productId) {
		try {
			Product product = new Product();
			product = (Product) service.getProduct(productId);
			return Response.status(Response.Status.OK).entity(product).build();
		} catch(IOException jpe) {
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity("{\"message\": \""+ jpe.getMessage() +"\"}").build();
		} catch(Exception e) {
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity("{\"message\": \""+ e.getMessage() +"\"}").build();
		}
	}
	
	@GET
	@Path("/productImage/{productId}/{imageId}")
	@Produces({ "image/png" })
	public Response getProductImage(@PathParam("productId") int productId, @PathParam("imageId") int imageId) {
		try {
			Product product = service.getProduct(productId);
			String path = "C:\\Users\\m.tsietsi\\testbed\\images\\";
			String fileName = imageId + ".png";
			File file = new File(path + fileName);
			ResponseBuilder response = Response.ok((Object) file);
			response.header("Content-Disposition", "attachment; filename=" + fileName);
			
			return response.build();
		} catch(Exception e) {
			return Response.status(Response.Status.NOT_FOUND).entity("{\"message\": \""+ e.getMessage() +"\"}").build();
		}
	}
	
	@DELETE
	@Path("/product")
	@Consumes({ "application/json" })
	@Produces({ "application/json" })
	public Response deleteProduct(Product product) {
		try{ 
			service.deleteProduct(product);
			return Response.status(Response.Status.OK).build();
		} catch(IOException jpe) {
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity("{\"message\": \""+ jpe.getMessage() +"\"}").build();
		} catch (Exception e) {
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity("{\"message\": \""+ e.getMessage() +"\"}").build();
		}
	}
	
	@DELETE
	@Path("/product/{productId}")
	@Produces({ "application/json" })
	public Response deleteProductById(@PathParam("productId") int productId) {
		try { 
			service.deleteProductById(productId);
			return Response.status(Response.Status.OK).build();
		} catch(IOException jpe) {
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity("{\"message\": \""+ jpe.getMessage() +"\"}").build();
		} catch (Exception e) {
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity("{\"message\": \""+ e.getMessage() +"\"}").build();
		}
	}
	
	@OPTIONS
	@Path("/order")
	public Response allowPost() {
		
		return Response.status(Response.Status.OK)
				.header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
	            .header("Access-Control-Allow-Credentials", "true")
	            .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
	            .header("Access-Control-Max-Age", "1209600")
	            .build();
	}
	
	@PUT
	@Path("/uploadImage/{productId}")
	@Consumes({ "multipart/form-data" })
	@Produces({ "application/json" })
	public Response uploadProductImage(@PathParam("productId") int productId, @MultipartForm MyMultipartForm form) {
		try{ 
			int [] imageId = new int [5];
			ArrayList<ImageProductResponse> images = new ArrayList<ImageProductResponse>();
			
			Product product;
			product = service.getProduct(productId);
			
			if(form.getImga_input() != null) {
				imageId[0] = new Random().nextInt();
				if(imageId[0] < 1) imageId[0] = imageId[0] * -1;
				saveImage(productId, imageId[0], form.getImga_input(), images);
			}
			if(form.getImgb_input() != null) {
				imageId[1] = new Random().nextInt();
				if(imageId[1] < 1) imageId[1] = imageId[1] * -1;
				saveImage(productId, imageId[1], form.getImgb_input(), images);
			}
			if(form.getImgc_input() != null) {
				imageId[2] = new Random().nextInt();
				if(imageId[2] < 1) imageId[2] = imageId[2] * -1;
				saveImage(productId, imageId[2], form.getImgc_input(), images);
			}
			if(form.getImgd_input() != null) {
				imageId[3] = new Random().nextInt();
				if(imageId[3] < 1) imageId[3] = imageId[3] * -1;
				saveImage(productId, imageId[3], form.getImgd_input(), images);
			}
			if(form.getImge_input() != null) {
				imageId[4] = new Random().nextInt();
				if(imageId[4] < 1) imageId[4] = imageId[4] * -1;
				saveImage(productId, imageId[4], form.getImge_input(), images);
			}
			
			if(imageId[0] != 0) product.setImga(imageId[0]);
			if(imageId[1] != 0) product.setImgb(imageId[1]);
			if(imageId[2] != 0) product.setImgc(imageId[2]);
			if(imageId[3] != 0) product.setImgd(imageId[3]);
			if(imageId[4] != 0) product.setImge(imageId[4]);
			
			service.editProduct(product);
			
			return Response.status(Response.Status.OK).entity(images).build();
			
		} catch(Exception e) {
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity("{\"message\": \""+ e.getMessage() +"\"}").build();
		}
	}
	
	@PUT
	@Path("/editImage/{productId}/{imageId}")
	@Produces({ "application/json" })
	public Response editProductImage(@PathParam("productId") int productId, @PathParam("imageId") int imageId, @MultipartForm MyMultipartForm form) {
		return null;
	}
	
	private void saveImage(int productId, int imageId, InputStream upload, ArrayList<ImageProductResponse> images) {
		final String path = "C:\\Users\\m.tsietsi\\testbed\\images\\";
		//final String path = "/home/reedhouse/images/";
		
		String fileName = path + imageId + ".png";
		saveFile(upload, fileName);
		
		ImageProductResponse ipr = new ImageProductResponse();
		ipr.setImageId(imageId);
		ipr.setProductId(productId);
		
		images.add(ipr);
	}
	
	private void saveFile(InputStream uploadedInputStream, String serverLocation) {
        try {
            int read = 0;
            byte[] bytes = new byte[1024];
 
            OutputStream outputStream = new FileOutputStream(new File(serverLocation));
            while ((read = uploadedInputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	@GET
	@Path("/login")
	@Produces({ "application/json" })
	public Response getLogin() {
		return Response.status(Response.Status.OK).entity(new String("Authenticated?")).build();
	}
}
