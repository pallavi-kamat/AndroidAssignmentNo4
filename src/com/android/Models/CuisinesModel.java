package com.android.Models;

public class CuisinesModel {
	String id;
	String name;
	public CuisinesModel() {
		super();
	}
	public CuisinesModel(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
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
		return "CuisinesClass [id=" + id + ", name=" + name + "]";
	}
	

}
