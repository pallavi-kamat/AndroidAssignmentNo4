package com.android.ZomatoApplication;

import java.util.ArrayList; 
import java.util.List;
import java.util.Locale;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.android.helpers.AppStatus;
import com.android.helpers.Constants;
import com.android.restClient.RestClient;
import com.android.tasks.SplashTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

public class SplashActivity extends Activity {
	AppStatus appStatus;
	ProgressDialog progress;
	Handler handler;
	String cityName,cityId;
	double lat=18.52043,lon=73.856744;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		LocationListener ll = new mylocationlistener();
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, ll);

		appStatus = AppStatus.getInstance(this);
		handler = new Handler();
		progress = new ProgressDialog(this);

		startApp();
	}

	private void startApp() {
		
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				showprogress(true, "Loading", "In Process please wait...");
				if (appStatus.isOnline(SplashActivity.this)) {
					Log.v("SPLASH_SCREEN", "App is online");
					if (appStatus.isRegistered()) {
						Log.v("SPLASH_SCREEN", "App is registered!");
						getCityInfo();
//						SplashTask mSignInTask = new SplashTask(SplashActivity.this);
//						mSignInTask.execute();
						Intent intent = new Intent(SplashActivity.this,
								CuisinesActivity.class);
						intent.putExtra("cityId",cityId);
						startActivity(intent);
						finish();
					} 
					/*else {
						Log.v("SPLASH_SCREEN", "You are not registered!!!!");
						Intent intent_login = new Intent(SplashActivity.this,
								SignInActivity.class);
						startActivity(intent_login);
						finish();
					}*/
				} 
				else {
					Log.v("SPLASH_SCREEN", "You are not online!!!!");
					showprogress(false, "", "");
					message("Please check you internet connection!!");
					finish();
				}
				showprogress(false, "", "");
			}
		});
		t.start();
	}
	private void getCityInfo() {
		
		try {
			
			appStatus = AppStatus.getInstance(this);
			List<NameValuePair> params = new ArrayList<NameValuePair>(3);			
			params.add(new BasicNameValuePair("lat","18.52043"));
			params.add(new BasicNameValuePair("lon","73.856744"));
			params.add(new BasicNameValuePair("apikey", Constants.AUTH_KEY));

			String strJsonReponse = RestClient.getInstance(this).doApiCall(Constants.strLocation, "GET", params);
			
			Log.d("Data Response...######",String.valueOf(strJsonReponse));

			// Fetching web service Response
			// String strJsonReponse =
			// "[{\"title\":\"Invitation through Email\",\"user\":{\"email\":\"as@as.com\"},\"created_at\":\"2012-04-09T05:53:31Z\",\"id\":43},{\"title\":\"Sample Victory\",\"user\":{\"email\":\"mahesh@yopmail.com\"},\"created_at\":\"2012-04-10T15:56:32Z\",\"id\":49}]";
			
				JSONObject responseObj=new JSONObject(strJsonReponse);
				JSONObject localityObj=responseObj.getJSONObject("locality");
				cityName=localityObj.getString("city_name");
				cityId=localityObj.getString("city_id");
				Log.d("city Id", cityId);
				Log.d("city Name", cityName);
				
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	void showprogress(final boolean show, final String title, final String msg) {
		handler.post(new Runnable() {
			@Override
			public void run() {
				if (show) {
					if (progress != null) {
						progress.setTitle(title);
						progress.setMessage(msg);
						progress.show();
					}
				} else {
					progress.cancel();
					progress.dismiss();
				}

			}
		});
	}

	void message(String msg) {
		final String mesage = msg;
		handler.post(new Runnable() {
			@Override
			public void run() {
				Toast toast = Toast.makeText(SplashActivity.this, mesage, 8000);
				toast.show();
			}
		});
	}

public class mylocationlistener implements LocationListener{

	@Override
	public void onLocationChanged(Location location) {
		if (location != null) {
			Log.d("LOCATION CHANGED", location.getLatitude() + "");
			Log.d("LOCATION CHANGED", location.getLongitude() + "");
			//   Toast.makeText(SplashActivity.this,String.format("%f,%f",location.getLatitude(),location.getLongitude()),Toast.LENGTH_SHORT).show();
			Geocoder geocoder = new Geocoder(SplashActivity.this, Locale.getDefault());
			List<Address> addresses;
			try
			{
				addresses= geocoder.getFromLocation(18.52043,73.856744, 1);
				Log.d("Address: ", addresses.get(0).getLocality());
			}
			catch (Exception e) {
				// TODO: handle exception
			}


		}
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}
}
public void warningDialogBox(String warningText) {
	// TODO Auto-generated method stub

	// prepare the alert box
	AlertDialog.Builder alertbox = new AlertDialog.Builder(
			SplashActivity.this);

	// set the message to display
	alertbox.setMessage(warningText);

	// add a neutral button to the alert box and assign a click listener
	alertbox.setNeutralButton("Ok", new DialogInterface.OnClickListener() {

		// click listener on the alert box
		public void onClick(DialogInterface arg0, int arg1) {
			// the button was clicked
		}
	});

	// show it
	alertbox.show();
}
}

