package com.android.restClient;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.util.Log;

public class RestClient {
	
//	public static String base_url = "http://192.168.0.199/apis/";
	public static String base_url = "https://api.zomato.com/v1/";

	public static String getBase_url() {
		return base_url;
	}

	public static void setBase_url(String base_url) {
		RestClient.base_url = base_url;
	}

	static RestClient instance = new RestClient();
	static Context context;

	public static RestClient getInstance(Context ctx) {
		context = ctx;
		return instance;

	}

	public String doApiCall(String url, String type,
			List<NameValuePair> nameValuePairs) throws ClientProtocolException,
			IOException {
		Log.d("base url: ", base_url);

		String result = "null";

		HttpClient httpclient = new DefaultHttpClient();

		if (type.equalsIgnoreCase("POST")) {

			HttpPost httpPost = new HttpPost(base_url + url);
			Log.v("RestClient", "#############POST URL: " + base_url + url);
			httpPost.setHeader("accept", "text/mobile");

			if (nameValuePairs != null && !nameValuePairs.isEmpty()) {

				httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			}

			HttpResponse response = httpclient.execute(httpPost);
			result = EntityUtils.toString(response.getEntity());
			return result;

		} else if (type.equalsIgnoreCase("GET")) {
			
			if (nameValuePairs != null && !nameValuePairs.isEmpty()) {
				
				url += "?";

				Iterator<NameValuePair> iter = nameValuePairs.iterator();
				while (iter.hasNext()) {

					NameValuePair nvp = iter.next();
					url += nvp.getName();
					url += "=";
					url += nvp.getValue();
					if (iter.hasNext())
						url += "&";

				}
				Log.d("base url: ", base_url);
			}

			Log.v("REstClient", "####url GET: " + base_url+url);
			HttpGet httpGet = new HttpGet(base_url + url);
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			httpGet.setHeader("accept", "text/mobile");
			result = httpclient.execute(httpGet, responseHandler);
			return result;

		}

		return result;
	}

}
