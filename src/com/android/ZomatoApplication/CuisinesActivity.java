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
import com.android.helpers.AppStatus;
import com.android.helpers.Constants;
import com.android.restClient.RestClient;
import com.android.tasks.CuisinesTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class CuisinesActivity extends Activity {
	String cityId;
	ListView list;
	ArrayList<CuisinesModel> cuisinesArray;
	SQLiteDatabase db;
	DatabaseFunctions function=new DatabaseFunctions();
	AppStatus appStatus;
	boolean isBackBtnPressed = false;
	boolean isPostedByFlag = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cuisines);



		MyApplication app=(MyApplication) getApplication();
		db=app.getDatabase(this);

		checkConnectivity();

	}

	public void checkConnectivity() {
		appStatus = AppStatus.getInstance(this);
		if (appStatus.isOnline(CuisinesActivity.this)) {
			Intent intent=getIntent();
			cityId=(String)intent.getSerializableExtra("cityId");
			CuisinesTask cuisineTask = new CuisinesTask(CuisinesActivity.this,cityId);
			cuisineTask.execute();
		} else {
			Toast.makeText(getApplicationContext(), "Please check your internet connection!!", Toast.LENGTH_SHORT);
			cuisinesArray=function.retriveCuisines(db);
			displayList();
			
			//	dismissDialog(0);
			//	showMessage("Please check you internet connection!!");
		}

	}
	public void getCuisinesInfo(ArrayList<CuisinesModel> cuisineArray) {



		//			List<NameValuePair> params = new ArrayList<NameValuePair>(2);			
		//			params.add(new BasicNameValuePair("city_id",cityId));
		//			params.add(new BasicNameValuePair("apikey", Constants.AUTH_KEY));
		//
		//			String strJsonReponse = RestClient.getInstance(this).doApiCall(Constants.strCuisines, "GET", params);
		//			
		//		//	Log.d("Cuisines Data Response...######",String.valueOf(strJsonReponse));
		//			CuisinesParser parser=new CuisinesParser();
		//			cuisineArray=parser.parseCuisines(strJsonReponse);					

		//			showDialog(0);
		//			ProgressDialog dialog12 = ProgressDialog.show(FeedsActivity.this, "", 
		//	                "Loading. Please wait...", true);
		//			showDialog(0);

		function.deleteAll(db, "cuisines");	
		function.storeCuisines(cuisineArray, db);
		cuisinesArray=function.retriveCuisines(db);
		db.close();

		//			for(CuisinesModel c:arr)
		//			{
		//				Log.d("Database data(Cuisines): ", c.toString());
		//			}


		displayList();

	}
	
	protected void displayList()
	{
		list=(ListView)findViewById(R.id.list);
		CuisinesAdapter adapter=new CuisinesAdapter(CuisinesActivity.this, cuisinesArray);
		list.setAdapter(adapter);
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				CuisinesModel cuisine=cuisinesArray.get(position);


				Intent intent=new Intent(CuisinesActivity.this,RestaurantActivity.class);
				intent.putExtra("cuisineId", cuisine.getId());
				intent.putExtra("cityId", cityId);
				startActivity(intent);

			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_DOWN) {
			switch (keyCode) {
			case KeyEvent.KEYCODE_BACK:
				isBackBtnPressed = true;
				Log.i("Backup Buttonnn   ####", "Pressed");
				warningDialogBox();

				break;
			case KeyEvent.KEYCODE_MENU:
				if (isBackBtnPressed)
					// unLock();
					break;
			default:
				isBackBtnPressed = false;
				// showMessage();
				break;
			}
		}
		return true;
	}
	private void warningDialogBox() {
		Log.i("Warning......Dialog", "ssssss");
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Are you sure you want to exit?")
				.setCancelable(false)
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								finish();
								return;
							}
						})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}
}
