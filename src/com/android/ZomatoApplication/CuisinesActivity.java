package com.android.ZomatoApplication;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.android.Database.DatabaseFunctions;
import com.android.Database.MyApplication;
import com.android.Models.CuisinesModel;
import com.android.Models.RestaurantModel;
import com.android.MyAdapters.CuisinesAdapter;
import com.android.Parsers.CuisinesParser;
import com.android.helpers.Constants;
import com.android.restClient.RestClient;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class CuisinesActivity extends Activity {
	String cityId;
	ListView list;
	ArrayList<CuisinesModel> cuisineArray;
	SQLiteDatabase db;
	DatabaseFunctions function=new DatabaseFunctions();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cuisines);
		Intent intent=getIntent();
		cityId=(String)intent.getSerializableExtra("cityId");
		Log.d("City id: cuisines", cityId);		

		MyApplication app=(MyApplication) getApplication();
		db=app.getDatabase(this);
		
		getCuisinesInfo();
		
	}
private void getCuisinesInfo() {
		
		try {
			
			List<NameValuePair> params = new ArrayList<NameValuePair>(2);			
			params.add(new BasicNameValuePair("city_id",cityId));
			params.add(new BasicNameValuePair("apikey", Constants.AUTH_KEY));

			String strJsonReponse = RestClient.getInstance(this).doApiCall(Constants.strCuisines, "GET", params);
			
		//	Log.d("Cuisines Data Response...######",String.valueOf(strJsonReponse));
			CuisinesParser parser=new CuisinesParser();
			cuisineArray=parser.parseCuisines(strJsonReponse);					
			

			function.deleteAll(db, "cuisines");	
			function.storeCuisines(cuisineArray, db);
			ArrayList<CuisinesModel> arr=function.retriveCuisines(db);
			for(CuisinesModel c:arr)
			{
				Log.d("Database data(Cuisines): ", c.toString());
			}
			
			
			list=(ListView)findViewById(R.id.list);
			CuisinesAdapter adapter=new CuisinesAdapter(CuisinesActivity.this, cuisineArray);
			list.setAdapter(adapter);
			list.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int position, long arg3) {
					CuisinesModel cuisine=cuisineArray.get(position);
					Intent intent=new Intent(CuisinesActivity.this,RestaurantActivity.class);
					intent.putExtra("cuisineId", cuisine.getId());
					intent.putExtra("cityId", cityId);
					startActivity(intent);
					
				}
			});
				
				
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
