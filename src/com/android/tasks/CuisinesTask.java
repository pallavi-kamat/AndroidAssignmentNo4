package com.android.tasks;

import android.content.Context;
import android.os.AsyncTask;

public class CuisinesTask extends AsyncTask<String, Void, String> {
	Context context;

	public CuisinesTask(Context context) {
		this.context=context;
	}

	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
	}

}
