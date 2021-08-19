package com.example.sctravelbuddyjavaapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{
	
	
	static final String DATABASE_NAME = "CITYINFO.db";
	static final int DATABASE_VERSION = 1;
	
	static final String DATABASE_TABLE = "Info_city";
	static final String City = "city_selected";
	static final String Place = "place_name";
	static final String Address = "address";
	static final String Description = "description";
	static final String Type = "type";
	
	public static final String CREATE_DB_QUERY = "CREATE TABLE IF NOT EXISTS " + DATABASE_TABLE + "(" + City + " VARCHAR," + Place + " VARCHAR,"  + Address + " VARCHAR,"  + Description + " VARCHAR,"  + Type + " VARCHAR"  + ")";
	
	
    public DatabaseHelper(Context context) {
		super(context,DATABASE_NAME,null,DATABASE_VERSION);
	}
	
	@Override
    public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_DB_QUERY);
	}
	
	@Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
	}
	
}
