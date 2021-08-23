/**
 * Smart Cities Travel Buddy
 * Created by Sriram, Simhendra and Anish, 2021
 * Course- Mobile Application Development, 6th sem, RVCE
 */

package com.example.sctravelbuddyjavaapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLDataException;

/**
 * Class to interact with and handle common database operations for the Attraction-related SQLite database
 */
public class DatabaseManager{
	private DatabaseHelper dbHelper;
	private Context context;
	private SQLiteDatabase database;

	// Constructor
	public DatabaseManager(Context ctx){
		context = ctx;
	}


	// Used to open connection to the database and to instantiate the necessary database objects needed for database operations
	public DatabaseManager open() throws SQLDataException{
		dbHelper = new DatabaseHelper(context);
		database = dbHelper.getWritableDatabase();
		return this;
	}

	// Used to close the connection to the database
	public void close(){
		dbHelper.close();
	}

	// Used to insert some sample values into the database when the app is run for the first time
	public void pre_insert(){
		Cursor c = fetch_all();
		if(c.getCount()>=12)
			return;
		System.out.println("Inserting initial rows");
		database.beginTransaction();
		ContentValues contentValues1 = new ContentValues();
		contentValues1.put(DatabaseHelper.City,"Bangalore");
		contentValues1.put(DatabaseHelper.Place,"Vidhan Soudha");
		contentValues1.put(DatabaseHelper.Address, "Ambedkar Bheedhi, Sampangi Rama Nagara, Bengaluru, Karnataka 560001");
		contentValues1.put(DatabaseHelper.Description,"State Government Office");
		contentValues1.put(DatabaseHelper.Type, "tourist");
		contentValues1.put(DatabaseHelper.CityDescription, "Garden City of India");
		database.insert(DatabaseHelper.DATABASE_TABLE,null,contentValues1);

		ContentValues contentValues2 = new ContentValues();
		contentValues2.put(DatabaseHelper.City,"Bangalore");
		contentValues2.put(DatabaseHelper.Place,"ITC Windsor");
		contentValues2.put(DatabaseHelper.Address,"Windsor Square, 25, Golf Course Road, Bengaluru, Karnataka 560052");
		contentValues2.put(DatabaseHelper.Description,"World renowned 5 star hotel");
		contentValues2.put(DatabaseHelper.Type, "hotel");
		database.insert(DatabaseHelper.DATABASE_TABLE,null,contentValues2);

		ContentValues contentValues3 = new ContentValues();
		contentValues3.put(DatabaseHelper.City,"Delhi");
		contentValues3.put(DatabaseHelper.Place, "Taj Mahal");
		contentValues3.put(DatabaseHelper.Address,"Dharmapuri, Forest Colony, Tajganj, Agra, Uttar Pradesh 282001");
		contentValues3.put(DatabaseHelper.Description,"Made by the Mughal Emperor Shah Jahan");
		contentValues3.put(DatabaseHelper.Type,"tourist");
		contentValues3.put(DatabaseHelper.CityDescription, "Capital of India");
		database.insert(DatabaseHelper.DATABASE_TABLE,null,contentValues3);

		ContentValues contentValues4 = new ContentValues();
		contentValues4.put(DatabaseHelper.City, "Delhi");
		contentValues4.put(DatabaseHelper.Place, "Indian Accent");
		contentValues4.put(DatabaseHelper.Address,"The Lodhi, Lodhi Rd, CGO Complex, Pragati Vihar, New Delhi, Delhi 110003");
		contentValues4.put(DatabaseHelper.Description,"Contemporary twists on Indian cuisine served at this refined restaurant with glass walls & ceilings.");
		contentValues4.put(DatabaseHelper.Type,"restaurant");
		database.insert(DatabaseHelper.DATABASE_TABLE,null,contentValues4);

		ContentValues contentValues5 = new ContentValues();
		contentValues5.put(DatabaseHelper.City,"Delhi");
		contentValues5.put(DatabaseHelper.Place,"Peshawari Chicken Corner");
		contentValues5.put(DatabaseHelper.Address, "33-34B, Prehlad Market, Ramjas Road, Karol Bagh, New Delhi, Delhi 110005");
		contentValues5.put(DatabaseHelper.Description,"Classic preparations of chicken include Soya Malai and Changezi at this longstanding chicken venue");
		contentValues5.put(DatabaseHelper.Type,"restaurant");
		database.insert(DatabaseHelper.DATABASE_TABLE,null,contentValues5);

		ContentValues contentValues6 = new ContentValues();
		contentValues6.put(DatabaseHelper.City,"Kolkata");
		contentValues6.put(DatabaseHelper.Place,"Victoria Memorial");
		contentValues6.put(DatabaseHelper.Address,"Victoria Memorial Hall, 1, Queens Way, Maidan, Kolkata, West Bengal 700071");
		contentValues6.put(DatabaseHelper.Description,"Dedicated to the memory of Empress Victoria");
		contentValues6.put(DatabaseHelper.Type,"tourist");
		contentValues6.put(DatabaseHelper.CityDescription, "Cultural Capital of India");
		database.insert(DatabaseHelper.DATABASE_TABLE,null,contentValues6);

		ContentValues contentValues7 = new ContentValues();
		contentValues7.put(DatabaseHelper.City,"Kolkata");
		contentValues7.put(DatabaseHelper.Place,"JW Marriott Hotel Kolkata");
		contentValues7.put(DatabaseHelper.Address,"4A, JBS Haldane Ave, Tangra, Kolkata, West Bengal 700105");
		contentValues7.put(DatabaseHelper.Description,"Highly esteemed 5 star Luxury Hotel");
		contentValues7.put(DatabaseHelper.Type,"hotel");
		database.insert(DatabaseHelper.DATABASE_TABLE,null,contentValues7);

		ContentValues contentValues8 = new ContentValues();
		contentValues8.put(DatabaseHelper.City,"Chennai");
		contentValues8.put(DatabaseHelper.Place,"ITC Grand Chola");
		contentValues8.put(DatabaseHelper.Address,"63, Anna Salai, Little Mount, Guindy, Chennai, Tamil Nadu 600032");
		contentValues8.put(DatabaseHelper.Description,"ITC Grand Chola is one of the world's largest hotels accredited with LEED Platinum certification");
		contentValues8.put(DatabaseHelper.Type,"hotel");
		contentValues8.put(DatabaseHelper.CityDescription, "Gateway to South India");
		database.insert(DatabaseHelper.DATABASE_TABLE,null,contentValues8);

		ContentValues contentValues9 = new ContentValues();
		contentValues9.put(DatabaseHelper.City,"Chennai");
		contentValues9.put(DatabaseHelper.Place,"The Government Museum, Chennai");
		contentValues9.put(DatabaseHelper.Address,"Government Maternity Hospital, Pantheon Rd, Egmore, Chennai, Tamil Nadu 600008");
		contentValues9.put(DatabaseHelper.Description,"Museum of human history and culture located in the Government Museum Complex in the neighbourhood of Egmore");
		contentValues9.put(DatabaseHelper.Type,"tourist");
		database.insert(DatabaseHelper.DATABASE_TABLE,null,contentValues9);

		ContentValues contentValues10 = new ContentValues();
		contentValues10.put(DatabaseHelper.City,"Hyderabad");
		contentValues10.put(DatabaseHelper.Place,"Golconda Fort");
		contentValues10.put(DatabaseHelper.Address,"Khair Complex, Ibrahim Bagh, Hyderabad, Telangana 500008");
		contentValues10.put(DatabaseHelper.Description,"Golconda Fort, also known as Golla konda, is a fortified citadel built by the Kakatiyas");
		contentValues10.put(DatabaseHelper.Type,"tourist");
		contentValues10.put(DatabaseHelper.CityDescription, "City of Pearls");
		database.insert(DatabaseHelper.DATABASE_TABLE,null,contentValues10);

		ContentValues contentValues11 = new ContentValues();
		contentValues11.put(DatabaseHelper.City,"Hyderabad");
		contentValues11.put(DatabaseHelper.Place,"Charminar");
		contentValues11.put(DatabaseHelper.Address,"Charminar Rd, Char Kaman, Ghansi Bazaar, Hyderabad, Telangana 500002");
		contentValues11.put(DatabaseHelper.Description,"The landmark has become known globally as a symbol of Hyderabad and is listed among the most recognised structures in India.");
		contentValues11.put(DatabaseHelper.Type,"tourist");
		database.insert(DatabaseHelper.DATABASE_TABLE,null,contentValues11);

		ContentValues contentValues12 = new ContentValues();
		contentValues12.put(DatabaseHelper.City,"Mumbai");
		contentValues12.put(DatabaseHelper.Place,"Gateway of India");
		contentValues12.put(DatabaseHelper.Address,"Apollo Bandar, Colaba, Mumbai, Maharashtra 400001");
		contentValues12.put(DatabaseHelper.Description,"Arch-monument built in the early 20th century");
		contentValues12.put(DatabaseHelper.Type,"tourist");
		contentValues12.put(DatabaseHelper.CityDescription, "Financial Capital of India");
		database.insert(DatabaseHelper.DATABASE_TABLE,null,contentValues12);



		database.setTransactionSuccessful();
		database.endTransaction();
		System.out.println("All done");

	}

	// Used to insert any given values into the database, used by AddAttraction class
	public void insert(String city,String place,String address,String description, String type){
		database.beginTransaction();
		ContentValues contentValues = new ContentValues();
		contentValues.put(DatabaseHelper.City,city);
		contentValues.put(DatabaseHelper.Place,place);
		contentValues.put(DatabaseHelper.Address,address);
		contentValues.put(DatabaseHelper.Description,description);
		contentValues.put(DatabaseHelper.Type, type);
		database.insert(DatabaseHelper.DATABASE_TABLE,null,contentValues);
		database.setTransactionSuccessful();
		database.endTransaction();
		System.out.println("Insertion Successful");

	}


	/**
	 * Used to fetch all unique attraction rows from the database for a given city
	 * @param cityname is the city for which the attractions should be returned
	 * @return cursor object to read the fetched data in the main program
	 */
	public Cursor fetch(String cityname){
		Cursor cursor = database.rawQuery(String.format("select DISTINCT * from %s where %s='%s';",DatabaseHelper.DATABASE_TABLE, DatabaseHelper.City, cityname),null);
		if(cursor!=null){
			cursor.moveToFirst();
		}
		return cursor;
	}

	/**
	 * Used to fetch all unique attraction rows from the database for all cities
	 * @return cursor object to read the fetched data
	 */
	public Cursor fetch_all(){
		Cursor cursor = database.rawQuery(String.format("select DISTINCT * from %s;",DatabaseHelper.DATABASE_TABLE),null);
		if(cursor!=null){
			cursor.moveToFirst();
		}
		return cursor;
	}

}