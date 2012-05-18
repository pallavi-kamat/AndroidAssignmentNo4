package com.android.helpers;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

// Used to store app's status related data like logins, auth_keys, online_status, highest scores, etc, 
// always remember to remove the logs when you go to production, as this can be a security hazard.

public class AppStatus {

	private static AppStatus instance = new AppStatus();
	ConnectivityManager connectivityManager;
	NetworkInfo wifiInfo, mobileInfo;
	private String auth_key;
	private String device_key;
	static Context context;
	boolean connected = false;

	public static AppStatus getInstance(Context ctx) {

		context = ctx;

		return instance;
	}

	public AppStatus() {

		this.auth_key = "null";
		this.setDevice_key("null");

	}

	public Boolean isOnline(Context con) {

		try {
			System.out.println("Ping URL:  " + pingUrl());
			connectivityManager = (ConnectivityManager) con
					.getSystemService(Context.CONNECTIVITY_SERVICE);

			NetworkInfo networkInfo = connectivityManager
					.getActiveNetworkInfo();
			connected = networkInfo != null && networkInfo.isAvailable()
					&& networkInfo.isConnected() && pingUrl();
			Log.i("Connected::::", String.valueOf(connected));
			return connected;

		} catch (Exception e) {
			System.out
					.println("CheckConnectivity Exception: " + e.getMessage());
			Log.v("connectivity", e.toString());
		}

		return connected;
	}

	public static boolean pingUrl() {
		try {
			String address = "http://www.google.com";
			final URL url = new URL(address);
			final HttpURLConnection urlConn = (HttpURLConnection) url
					.openConnection();
			urlConn.setConnectTimeout(1000 * 10); // mTimeout is in seconds
			final long startTime = System.currentTimeMillis();
			urlConn.connect();
			final long endTime = System.currentTimeMillis();
			if (urlConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				System.out.println("Time (ms) : " + (endTime - startTime));
				System.out.println("Ping to " + address + " was success");
				return true;
			}
		} catch (final MalformedURLException e1) {
			e1.printStackTrace();
		} catch (final IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public Boolean isRegistered() {
		try {
			if (Constants.AUTH_KEY.equalsIgnoreCase(
					"null"))
				return false;
			else
				return true;
		} catch (Exception e) {
			System.out.println("CheckUpdate Exception: " + e.getMessage());
			Log.d("Update", e.toString());
		}

		return false;

	}

	public String getSharedStringValue(String key) {
		SharedPreferences sp = context.getSharedPreferences(
				Constants.strFileName, 0);
		String value = sp.getString(key, null);
		Log.i("Fetched ShareValues:::::", value);
		return value;
	}

	public String getAuthKey() {
		return auth_key;
	}

	String readAuthKey() {
		SharedPreferences sp = context.getSharedPreferences("appdata", 0);
		return sp.getString("auth_key", "null");
	}

	public boolean saveAuthKey(String keyName,String key) {
		SharedPreferences sp = context.getSharedPreferences(
				Constants.strFileName, 0);
		Editor edit = sp.edit();
		edit.putString(keyName, key);
		return edit.commit();
	}

//	public void setAuthKey(String key) {
//		auth_key = key;
//		saveAuthKey(key);
//	}

	public String getDeviceKey() {
		return readDeviceKey();
	}

	String readDeviceKey() {
		SharedPreferences sp = context.getSharedPreferences("appdata", 0);
		return sp.getString("device_key", "null");
	}

	boolean saveDeviceKey(String key) {
		SharedPreferences sp = context.getSharedPreferences("appdata", 0);
		Editor edit = sp.edit();
		edit.putString("device_key", key);
		return edit.commit();
	}

	public void setDeviceKey(String key) {
		setDevice_key(key);
		saveDeviceKey(key);
		Log.v("AppStatus", "#######################" + key);
	}

	public void saveUserData(String user_name, String user_email,
			String user_no, boolean b) {
		// TODO Auto-generated method stub

	}

	public String getDevice_key() {
		return device_key;
	}

	public void setDevice_key(String device_key) {
		this.device_key = device_key;
	}

}