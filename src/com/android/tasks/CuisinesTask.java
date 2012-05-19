package com.android.tasks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;

import com.android.Models.CuisinesModel;
import com.android.Parsers.CuisinesParser;
import com.android.ZomatoApplication.CuisinesActivity;
import com.android.helpers.Constants;
import com.android.restClient.RestClient;

import android.content.Intent;
import android.os.AsyncTask;

public class CuisinesTask extends AsyncTask<String, Void, String> {
	CuisinesActivity context;
	String cityId;

	public CuisinesTask(CuisinesActivity context,String cityId) {
		this.context=context;
		this.cityId=cityId;
	}

	@Override
	protected String doInBackground(String... param) {
		String strJsonReponse=null;
		try {
			List<NameValuePair> params = new ArrayList<NameValuePair>(2);			
			params.add(new BasicNameValuePair("city_id",cityId));
			params.add(new BasicNameValuePair("apikey", Constants.AUTH_KEY));

			strJsonReponse = RestClient.getInstance(context).doApiCall(Constants.strCuisines, "GET", params);
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
		CuisinesParser parser=new CuisinesParser();
		ArrayList<CuisinesModel> cuisineArray=parser.parseCuisines(strJsonReponse);
		context.getCuisinesInfo(cuisineArray);
		
		super.onPostExecute(strJsonReponse);
	}

}
