package com.classes;

import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

/*
 * An extension of ParseObject that makes
 * it more convenient to access information
 * about a given Meal 
 */

@ParseClassName("Meal")
public class Meal extends ParseObject {

	public Meal() {
		// A default constructor is required.
	}

	public String getTitle() {
		return getString("title");
	}

	public void setTitle(String title) {
		put("title", title);
	}

	public ParseUser getAuthor() {
		return getParseUser("author");
	}

	public void setAuthor(ParseUser user) {
		put("author", user);
	}

	public int getRating() {
		return getInt("rating");
	}

	public void setRating(int rating) {
		put("rating", rating);
	}

	public ParseFile getPhotoFile() {
		return getParseFile("photo");
	}

	public void setPhotoFile(ParseFile file) {
		put("photo", file);
	}
	
	/* Converts a bitmap into byte[] to be stored in backend
	 * @param bm The bitmap to be converted
	 */
	public byte[] bitmapToByte(Bitmap bm){
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
		byte[] byteArray = stream.toByteArray();
		return byteArray;
	}
	
	

}
