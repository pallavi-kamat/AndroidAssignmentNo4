package com.android.Database;


import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class MyApplication extends Application{
	
	SQLiteDatabase db;
	MyHelper helper;
	
	public MyApplication() {
		super();
	}
	
	@Override
	public void onCreate() {		
		super.onCreate();
		
	}
	
	public SQLiteDatabase getDatabase(Context context)
	{	
		helper=new MyHelper(getApplicationContext());
		db=helper.getWritableDatabase();
		return db;
	}	
	

}
