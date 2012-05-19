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
import com.android.helpers.AppStatus;
import com.android.helpers.Constants;
import com.android.restClient.RestClient;
import com.android.tasks.CuisinesTask;
import com.android.tasks.RestaurantsTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ListView;
public class RestaurantActivity extends Activity {
	String cuisineId,cityId;
	ArrayList<RestaurantModel> restArray;
	ListView list;
	SQLiteDatabase db;
	DatabaseFunctions function=new DatabaseFunctions();
	AppStatus appStatus;
	boolean isBackBtnPressed = false;
	boolean isPostedByFlag = true;
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

		checkConnectivity();

	}

	public void checkConnectivity() {
		appStatus = AppStatus.getInstance(this);
		if (appStatus.isOnline(RestaurantActivity.this)) {

			RestaurantsTask restaurantTask = new RestaurantsTask(RestaurantActivity.this,cityId,cuisineId);
			restaurantTask.execute();
		} else {
			restArray=function.retriveRestaurants(db,cuisineId);
			displayList();
			//	dismissDialog(0);
			//	showMessage("Please check you internet connection!!");
		}

	}

	public void getRestaurantsInfo(ArrayList<RestaurantModel> restaurantsArray) {



		//			List<NameValuePair> params = new ArrayList<NameValuePair>(2);			
		//			params.add(new BasicNameValuePair("city_id",cityId));
		//			params.add(new BasicNameValuePair("cuisine_id", cuisineId));
		//			params.add(new BasicNameValuePair("apikey", Constants.AUTH_KEY));
		//
		//			String strJsonReponse = RestClient.getInstance(this).doApiCall(Constants.strSearch, "GET", params);
		//			
		//		//	Log.d("Cuisines Data Response...######",String.valueOf(strJsonReponse));
		//			RestaurantParser parser=new RestaurantParser();
		//			restaurantsArray=parser.parseRestaurants(strJsonReponse);

		//	function.deleteAll(db, "restaurants");
		function.storeRestaurants(restaurantsArray, db);
		restArray=function.retriveRestaurants(db,cuisineId);
		db.close();
		//			for(RestaurantModel c:restaurantsArray)
		//			{
		//				Log.d("Database data(Restaurants): ", c.toString());
		//			}
		//			

		displayList();

	}
	protected void displayList()
	{
		RestaurantAdapter adapter=new RestaurantAdapter(this, restArray);
		list=(ListView)findViewById(R.id.list);		
		list.setAdapter(adapter);
	}
}