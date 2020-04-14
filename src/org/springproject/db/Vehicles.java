package org.springproject.db;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Vehicles implements Serializable {
	@Id
	private int id;
	private String name;
	
	
	public Vehicles() {
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


	@Override
	public String toString() {
		return "Vehicles [id=" + id + ", name=" + name + "]";
	}
	
	
	
	
}
