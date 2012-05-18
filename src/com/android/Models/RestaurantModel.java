package com.android.Models;

public class RestaurantModel {
	String cuisineId;
	String name;
	String address;
	String cuisines;
	float rating;
	public RestaurantModel() {
		super();
	}
	public RestaurantModel(String cuisineId,String name, String address, String cuisines,
			float rating) {
		super();
		this.cuisineId=cuisineId;
		this.name = name;
		this.address = address;
		this.cuisines = cuisines;
		this.rating = rating;
	}
	public String getCuisineId() {
		return cuisineId;
	}
	public void setCuisineId(String cuisineId) {
		this.cuisineId = cuisineId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCuisines() {
		return cuisines;
	}
	public void setCuisines(String cuisines) {
		this.cuisines = cuisines;
	}
	public float getRating() {
		return rating;
	}
	public void setRating(float rating) {
		this.rating = rating;
	}
	@Override
	public String toString() {
		return "RestaurantModel [name=" + name + ", address=" + address
				+ ", cuisines=" + cuisines + ", rating=" + rating + "]";
	}
	
	
	
	

}
