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

package org.rhs.ecommerce.hibernate;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/* 
 * @author mtsietsi
 */

@Entity
@Table(name="Product")

public class Product {

	@Id
	private int productId;
	@Transient
	private int id;
	private String name;
	private String description;
	private int imga;
	private int imgb;
	private int imgc;
	private int imgd;
	private int imge;
	private double price;
	private int quantity;
	private int shop;
	private int weight;
	private int category;
	private Date added;
	private Date modified;
	
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getImga() {
		return imga;
	}
	public void setImga(int imga) {
		this.imga = imga;
	}
	public int getImgb() {
		return imgb;
	}
	public void setImgb(int imgb) {
		this.imgb = imgb;
	}
	public int getImgc() {
		return imgc;
	}
	public void setImgc(int imgc) {
		this.imgc = imgc;
	}
	public int getImgd() {
		return imgd;
	}
	public void setImgd(int imgd) {
		this.imgd = imgd;
	}
	public int getImge() {
		return imge;
	}
	public void setImge(int imge) {
		this.imge = imge;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getShop() {
		return shop;
	}
	public void setShop(int shop) {
		this.shop = shop;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public int getCategory() {
		return category;
	}
	public void setCategory(int category) {
		this.category = category;
	}
	public Date getAdded() {
		return added;
	}
	public void setAdded(Date added) {
		this.added = added;
	}
	public Date getModified() {
		return modified;
	}
	public void setModified(Date modified) {
		this.modified = modified;
	}
}
