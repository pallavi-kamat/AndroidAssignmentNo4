package com.android.tasks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.ZomatoApplication.CuisinesActivity;
import com.android.helpers.Constants;
import com.android.restClient.RestClient;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

public class SplashTask extends AsyncTask<String, Void, String>{
	Context context;

	public SplashTask(Context context) {
		this.context=context;
	}
	@Override
	protected String doInBackground(String... param) {
		String strJsonReponse = null;
		try {
			List<NameValuePair> params = new ArrayList<NameValuePair>(3);			
			params.add(new BasicNameValuePair("lat","18.52043"));
			params.add(new BasicNameValuePair("lon","73.856744"));
			params.add(new BasicNameValuePair("apikey", com.android.helpers.Constants.AUTH_KEY));

			strJsonReponse = RestClient.getInstance(context).doApiCall(Constants.strLocation, "GET", params);

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
		// TODO Auto-generated method stub
		super.onPostExecute(strJsonReponse);
		try {
			JSONObject responseObj=new JSONObject(strJsonReponse);
			JSONObject localityObj=responseObj.getJSONObject("locality");
		//	String cityName=localityObj.getString("city_name");
			String cityId=localityObj.getString("city_id");
				

			Intent intent = new Intent(context,	CuisinesActivity.class);
			intent.putExtra("cityId",cityId);
			context.startActivity(intent);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
