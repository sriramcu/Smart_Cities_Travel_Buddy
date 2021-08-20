/**
 * Smart Cities Travel Buddy
 * Created by Sriram, Simhendra and Anish, 2021
 * Course- Mobile Application Development, 6th sem, RVCE
 */

package com.example.sctravelbuddyjavaapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Activity class to select a city to browse attractions in, by clicking on an image button corresponding to a city
 */
public class SelectorActivity extends Activity {

    /**
     * Method called when activity is launched
     * @param savedInstanceState saved state information
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_selector);
        DatabaseManager dbManager;
        dbManager = new DatabaseManager(getApplicationContext());
        try{
            dbManager.open();
        }
        catch(Exception e){
            e.printStackTrace();
        }

        // Inserts sample data into the database if the database is empty
        dbManager.pre_insert();
        dbManager.close();


        Button LogOUT = (Button)findViewById(R.id.logoutButton);

        // On click listener for logout button
        LogOUT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Finishing current activity on button click.
                finish();

                Toast.makeText(getApplicationContext(),"Log Out Successful", Toast.LENGTH_LONG).show();

            }
        });

        // Getting the name of the user who just logged in from the intent
        if(getIntent().hasExtra("MY_NAME")){
            String name1 = getIntent().getExtras().getString("MY_NAME");
            System.out.println(name1);
            TextView tv = (TextView) findViewById(R.id.welcomeMsg);
            String msg = "Welcome back, " + name1 + "!";
            tv.setText(msg);
        }
    }

    // Method called when an image button of a city is clicked, this is the common method for all City Image Buttons
    public void imageButtonClicked(View view){
        // Gets the city name of the image button clicked
        String city_selected = getResources().getResourceEntryName(view.getId());
        city_selected = city_selected.substring(6);
        System.out.println(city_selected);

        Intent startIntent = new Intent(getApplicationContext(), MapsActivity.class);
        startIntent.putExtra("CITY_NAME", city_selected);
        startActivity(startIntent);


    }
}
