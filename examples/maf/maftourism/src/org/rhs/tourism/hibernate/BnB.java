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

package org.rhs.tourism.hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/* 
 * @author mtsietsi
 */
@Entity
@Table(name="BnB")

public class BnB {
	@Id
	private int bnbId;
	@Transient
	private int userId;
	
	private String name;
	private String description;
	private int nobeds;
	private byte[] imga;
	private byte[] imgb;
	private byte[] imgc;
	private byte[] imgd;
	private byte[] imge;
	//TODO: Change this to a serializable composite field
	private String latlng; 
	private int price;
	private short available;
	private short breakfast;
	private short hotshower;
	private short braai;
	private short self;
	private short tv;
	private short tours;
	
	public int getBnbId() {
		return bnbId;
	}
	public void setBnbId(int bnbId) {
		this.bnbId = bnbId;
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
	public int getNobeds() {
		return nobeds;
	}
	public void setNobeds(int nobeds) {
		this.nobeds = nobeds;
	}
	public byte[] getImga() {
		return imga;
	}
	public void setImga(byte[] imga) {
		this.imga = imga;
	}
	public byte[] getImgb() {
		return imgb;
	}
	public void setImgb(byte[] imgb) {
		this.imgb = imgb;
	}
	public byte[] getImgc() {
		return imgc;
	}
	public void setImgc(byte[] imgc) {
		this.imgc = imgc;
	}
	public byte[] getImgd() {
		return imgd;
	}
	public void setImgd(byte[] imgd) {
		this.imgd = imgd;
	}
	public byte[] getImge() {
		return imge;
	}
	public void setImge(byte[] imge) {
		this.imge = imge;
	}
	public String getLatlng() {
		return latlng;
	}
	public void setLatlng(String latlng) {
		this.latlng = latlng;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int isAvailable() {
		return available;
	}
	public void setAvailable(short available) {
		this.available = available;
	}
	public short isBreakfast() {
		return breakfast;
	}
	public void setBreakfast(short breakfast) {
		this.breakfast = breakfast;
	}
	public short isHotshower() {
		return hotshower;
	}
	public void setHotshower(short hotshower) {
		this.hotshower = hotshower;
	}
	public short isBraai() {
		return braai;
	}
	public void setBraai(short braai) {
		this.braai = braai;
	}
	public short isSelf() {
		return self;
	}
	public void setSelf(short self) {
		this.self = self;
	}
	public short isTv() {
		return tv;
	}
	public void setTv(short tv) {
		this.tv = tv;
	}
	public short isTours() {
		return tours;
	}
	public void setTours(short tours) {
		this.tours = tours;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
}
