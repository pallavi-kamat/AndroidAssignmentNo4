package com.android.Parsers;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.android.Models.RestaurantModel;


public class RestaurantParser {
	ArrayList<RestaurantModel> restaurantsArray;

	public ArrayList<RestaurantModel> parseRestaurants(String strJsonReponse)
	{

		restaurantsArray=new ArrayList<RestaurantModel>();
		try
		{

			JSONObject responseRestaurant = new JSONObject(strJsonReponse);
			JSONArray restaurantObj = responseRestaurant.getJSONArray("results");

			for(int i=0;i < restaurantObj.length();i++)
			{
				RestaurantModel r=new RestaurantModel();
				JSONObject jObj=restaurantObj.getJSONObject(i);
				JSONObject restaurant=jObj.getJSONObject("result");

				r.setName(restaurant.getString("name").toString());
				r.setAddress(restaurant.getString("address").toString());
				r.setCuisines(restaurant.getString("cuisines").toString());
				r.setRating(Float.parseFloat(restaurant.getString("rating_editor_overall").toString()));

				restaurantsArray.add(r);

			}

		}
		catch (Exception e) {
			// TODO: handle exception
		}

		return restaurantsArray;
	}

}
