package org.springproject.db;

import java.io.Serializable;
import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Posts implements Serializable{
	
	@Id
	private int id;
	private String name;
	private int price;
	private int vehicle_fk;
	private String descrption;
	private String picture;

	
	
	public Posts() {
		super();
		
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


	public int getPrice() {
		return price;
	}


	public void setPrice(int price) {
		this.price = price;
	}


	public int getVehicle_fk() {
		return vehicle_fk;
	}


	public void setVehicle_fk(int vehicle_fk) {
		this.vehicle_fk = vehicle_fk;
	}


	public String getDescrption() {
		return descrption;
	}


	public void setDescrption(String descrption) {
		this.descrption = descrption;
	}


	public String getPicture() {
		return picture;
	}


	public void setPicture(String picture) {
		this.picture = picture;
	}


	@Override
	public String toString() {	
		return "Posts [id=" + id + ", name=" + name + ", price=" + price + ", vehicle_fk=" + vehicle_fk
				+ ", descrption=" + descrption + ", picture=" + picture + "]";
	}
	
	
}
