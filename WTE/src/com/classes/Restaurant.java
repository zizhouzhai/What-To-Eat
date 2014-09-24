package com.classes;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

@ParseClassName("Restaurant")
public class Restaurant extends ParseObject{


	/*
	 * Default constructor for a restaurant
	 */
	public Restaurant(){
		
	}

	public String getRestaurantName() {
		return getString("restaurantName");
	}

	public void setRestaurantName(String restaurantName) {
		put("restaurantName", restaurantName);
	}
	
	public int getRestaurantRating(){
		return getInt("restaurantRating");
	}
	
	public void setRestaurantRating(int rating){
		put("restaurantRating",rating);
	}
	
	public String getRestaurantAddress(){
		return getString("restaurantAddress");
	}
	
	public void setRestaurantAddress(String restaurantAddress){
		put("restaurantAddress",restaurantAddress);
	}
	
	public double getLatitude(){
		return this.getDouble("latitude");
	}
	
	public void setLatitude(double latitude){
		put("latitude", latitude);
	}
	
	public double getLongitude(){
		return this.getDouble("latitude");
	}
	
	public void setLongitude(double longitude){
		put("longitude", longitude);
	}
	
	public ParseFile getPhotoFile() {
		return getParseFile("photo");
	}

	public void setPhotoFile(ParseFile file) {
		put("photo", file);
	}
	
}
