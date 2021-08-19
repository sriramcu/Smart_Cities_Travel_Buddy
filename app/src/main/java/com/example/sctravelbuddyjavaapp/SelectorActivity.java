package com.example.sctravelbuddyjavaapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SelectorActivity extends Activity {
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
        dbManager.pre_insert();
        dbManager.close();


        Button LogOUT = (Button)findViewById(R.id.logoutButton);

        LogOUT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Finishing current activity on button click.
                finish();

                Toast.makeText(getApplicationContext(),"Log Out Successful", Toast.LENGTH_LONG).show();

            }
        });

        if(getIntent().hasExtra("MY_NAME")){
            String name1 = getIntent().getExtras().getString("MY_NAME");
            System.out.println(name1);
            TextView tv = (TextView) findViewById(R.id.welcomeMsg);
            String msg = "Welcome back, " + name1 + "!";
            tv.setText(msg);
        }
    }


    public void imageButtonClicked(View view){
        String city_selected = getResources().getResourceEntryName(view.getId());
        city_selected = city_selected.substring(6);
        System.out.println(city_selected);

        Intent startIntent = new Intent(getApplicationContext(), MapsActivity.class);
        startIntent.putExtra("CITY_NAME", city_selected);
        startActivity(startIntent);


    }
}
