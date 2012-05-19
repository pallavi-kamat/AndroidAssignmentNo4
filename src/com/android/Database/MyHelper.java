package com.android.Database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyHelper extends SQLiteOpenHelper{
	
	private static final String DATABASE_NAME = "ZomatoDatabase";
	private static final int DATABASE_VERSION = 1;
	
	public MyHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	

	@Override
	public void onCreate(SQLiteDatabase db) {
			db.execSQL("CREATE TABLE cuisines (cuisineId TEXT,name TEXT);");
		    db.execSQL("CREATE TABLE restaurants (cuisineId TEXT primarykey,name TEXT,address TEXT,cuisines TEXT,rating TEXT);");
								
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

}
