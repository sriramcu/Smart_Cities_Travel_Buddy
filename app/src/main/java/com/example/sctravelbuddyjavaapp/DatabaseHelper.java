/**
 * Smart Cities Travel Buddy
 * Created by Sriram, Simhendra and Anish, 2021
 * Course- Mobile Application Development, 6th sem, RVCE
 */
package com.example.sctravelbuddyjavaapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Class to represent the SQLite database used to store attraction-related information
 */
public class DatabaseHelper extends SQLiteOpenHelper{
	
	
	static final String DATABASE_NAME = "CITYINFO.db";
	static final int DATABASE_VERSION = 1;
	
	static final String DATABASE_TABLE = "Info_city";
	static final String City = "city_selected";
	static final String Place = "place_name";
	static final String Address = "address";
	static final String Description = "description";
	static final String Type = "type";
	static final String CityDescription = "city_description";
	
	public static final String CREATE_DB_QUERY = "CREATE TABLE IF NOT EXISTS " + DATABASE_TABLE + "(" + City + " VARCHAR," + Place + " VARCHAR,"  + Address + " VARCHAR,"  + Description + " VARCHAR,"  + Type + " VARCHAR," + CityDescription + " VARCHAR"  + ")";
	

	// Constructor
    public DatabaseHelper(Context context) {
		super(context,DATABASE_NAME,null,DATABASE_VERSION);
	}

	// Used to create database when this class is accessed
	@Override
    public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_DB_QUERY);
	}

	// Helper method to upgrade the SQLite database version
	@Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
	}
	
}
