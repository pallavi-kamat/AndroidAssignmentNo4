package com.android.Database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.android.Models.CuisinesModel;
import com.android.Models.RestaurantModel;

public class DatabaseFunctions {
	
	
	
	public void storeCuisines(ArrayList<CuisinesModel> cuisineArray,SQLiteDatabase db)
	{
		Log.d("***********","************");
		for(CuisinesModel c:cuisineArray)
		{
			ContentValues values=new ContentValues();
			values.put("cuisineId", c.getId());
			values.put("name", c.getName());
			db.insert("cuisines", null, values);
			
		}
	}
	public void storeRestaurants(ArrayList<RestaurantModel> restaurantArray,SQLiteDatabase db)
	{
		
		for(RestaurantModel r:restaurantArray)
		{
			ContentValues values=new ContentValues();
			values.put("cuisineId", r.getCuisineId());
			values.put("name", r.getName());
			values.put("address", r.getAddress());
			values.put("cuisines", r.getCuisines());
			values.put("rating", String.format("%f",r.getRating()));
			db.insert("restaurants", null, values);
			
		}
	}
	public ArrayList<CuisinesModel> retriveCuisines(SQLiteDatabase db)
	{
		Log.d("***********","************");
		ArrayList<CuisinesModel> arrayCuisines=new ArrayList<CuisinesModel>();
		Cursor cur=db.query("cuisines", new String[]{"cuisineId","name"}, null, null, null, null, null);

		if(cur.moveToFirst())
		{
			do
			{
				 CuisinesModel cuisine=new CuisinesModel();
				 cuisine.setId(cur.getString(cur.getColumnIndex("cuisineId")));
				cuisine.setName(cur.getString(cur.getColumnIndex("name")));
				Log.d("Data",cuisine.toString());
				arrayCuisines.add(cuisine);
			
			}while(cur.moveToNext());
		}
		cur.close();
		return arrayCuisines;
	}
	
	public ArrayList<RestaurantModel> retriveRestaurants(SQLiteDatabase db,String cuisineId)
	{
		ArrayList<RestaurantModel> arrayRestaurants=new ArrayList<RestaurantModel>();
		Cursor cur=db.query(true, "restaurants", new String[]{"cuisineId","name","address","cuisines","rating"},"cuisineId="+cuisineId,null, null, null, null, null);
	//	Cursor cur=db.query("restaurants", new String[]{"cuisineId","name","address","cuisines","rating"}, null, null, null, null, null);

		if(cur.moveToFirst())
		{
			do
			{
				RestaurantModel restaurant=new RestaurantModel();
				restaurant.setCuisineId(cur.getString(cur.getColumnIndex("cuisineId")));
				restaurant.setName(cur.getString(cur.getColumnIndex("name")));
				restaurant.setAddress(cur.getString(cur.getColumnIndex("address")));
				restaurant.setCuisines(cur.getString(cur.getColumnIndex("cuisines")));
				restaurant.setRating(Float.parseFloat(cur.getString(cur.getColumnIndex("rating"))));
				Log.d("Data",restaurant.toString());
				arrayRestaurants.add(restaurant);
			
			}while(cur.moveToNext());
		}
		cur.close();
		return arrayRestaurants;
	}
	
	public void deleteAll(SQLiteDatabase db,String tableName) {
		try {
			db.beginTransaction();
			db.delete(tableName, null, null);
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
	}

}
