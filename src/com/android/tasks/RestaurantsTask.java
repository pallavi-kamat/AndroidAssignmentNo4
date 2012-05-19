package com.android.tasks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;

import com.android.Models.RestaurantModel;
import com.android.Parsers.RestaurantParser;
import com.android.ZomatoApplication.CuisinesActivity;
import com.android.ZomatoApplication.RestaurantActivity;
import com.android.helpers.Constants;
import com.android.restClient.RestClient;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

public class RestaurantsTask extends AsyncTask<String, Void, String>{

	RestaurantActivity context;
	String cityId,cuisineId;
	public RestaurantsTask(RestaurantActivity context,String cityId,String cuisineId) {
		this.context=context;
		this.cityId=cityId;
		this.cuisineId=cuisineId;
	}
	@Override
	protected String doInBackground(String... param) {
		String strJsonReponse=null;
		try {
			List<NameValuePair> params = new ArrayList<NameValuePair>(2);			
			params.add(new BasicNameValuePair("city_id",cityId));
			params.add(new BasicNameValuePair("cuisine_id", cuisineId));
			params.add(new BasicNameValuePair("apikey", Constants.AUTH_KEY));

			strJsonReponse = RestClient.getInstance(context).doApiCall(Constants.strSearch, "GET", params);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return strJsonReponse;
	}
	@Override
	protected void onPostExecute(String strJsonReponse) {
		RestaurantParser parser=new RestaurantParser();
		ArrayList<RestaurantModel> restaurantsArray=parser.parseRestaurants(strJsonReponse,cuisineId);
		context.getRestaurantsInfo(restaurantsArray);
		super.onPostExecute(strJsonReponse);
	}

}
