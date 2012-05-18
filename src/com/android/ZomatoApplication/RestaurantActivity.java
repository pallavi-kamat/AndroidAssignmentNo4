package com.android.ZomatoApplication;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.android.Database.DatabaseFunctions;
import com.android.Database.MyApplication;
import com.android.Models.RestaurantModel;
import com.android.MyAdapters.RestaurantAdapter;
import com.android.Parsers.RestaurantParser;
import com.android.helpers.Constants;
import com.android.restClient.RestClient;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
public class RestaurantActivity extends Activity {
	String cuisineId,cityId;
	ArrayList<RestaurantModel> restaurantsArray;
	ListView list;
	SQLiteDatabase db;
	DatabaseFunctions function=new DatabaseFunctions();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.restaurants);
		Intent intent=getIntent();
		cuisineId=(String)intent.getSerializableExtra("cuisineId");
		cityId=(String)intent.getSerializableExtra("cityId");
		
		MyApplication app=(MyApplication) getApplication();
		db=app.getDatabase(this);
		
		getRestaurantsInfo();
		
	}
	
private void getRestaurantsInfo() {
		
		try {
			
			List<NameValuePair> params = new ArrayList<NameValuePair>(2);			
			params.add(new BasicNameValuePair("city_id",cityId));
			params.add(new BasicNameValuePair("cuisine_id", cuisineId));
			params.add(new BasicNameValuePair("apikey", Constants.AUTH_KEY));

			String strJsonReponse = RestClient.getInstance(this).doApiCall(Constants.strSearch, "GET", params);
			
		//	Log.d("Cuisines Data Response...######",String.valueOf(strJsonReponse));
			RestaurantParser parser=new RestaurantParser();
			restaurantsArray=parser.parseRestaurants(strJsonReponse);
			
			function.deleteAll(db, "restaurants");
			function.storeRestaurants(restaurantsArray, db);
			ArrayList<RestaurantModel> arr=function.retriveRestaurants(db);
			for(RestaurantModel c:arr)
			{
				Log.d("Database data(Restaurants): ", c.toString());
			}
			
			RestaurantAdapter adapter=new RestaurantAdapter(this, restaurantsArray);
			list=(ListView)findViewById(R.id.list);		
			list.setAdapter(adapter);		

				
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
