package com.example.sctravelbuddyjavaapp;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.w3c.dom.Attr;

/**
 * Activity class for admins to add an attraction to the app
 */
public class AddAttraction extends AppCompatActivity {

    EditText City;
    EditText AttrName;
    EditText Address;
    EditText AttrType;
    EditText Description;
    Button AddAttraction;
    Button Logout;

    /**
     * Method called when activity is launched
     * @param savedInstanceState saved state information
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_attraction);
        City = (EditText) findViewById(R.id.city_entry);
        AttrName = (EditText) findViewById(R.id.attrname_entry);
        Address = (EditText) findViewById(R.id.address_entry);
        AttrType = (EditText) findViewById(R.id.attrtype_entry);
        Description = (EditText) findViewById(R.id.attr_desc_entry);

        AddAttraction = (Button) findViewById(R.id.add_button);
        Logout = (Button) findViewById(R.id.logoutbuttonadmin);

        /*
         * On click listener for the add attraction button
         * On clicking the button a new attraction is saved
         * containing the following details - city name,
         * place name, address, type and description of the attraction
         */
        AddAttraction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseManager dbManager;
                dbManager = new DatabaseManager(getApplicationContext());
                try{
                    dbManager.open();
                }
                catch(Exception e){
                    e.printStackTrace();
                }
                dbManager.insert(City.getText().toString(), AttrName.getText().toString(), Address.getText().toString(), Description.getText().toString(), AttrType.getText().toString());
                dbManager.close();
                Toast.makeText(getApplicationContext(),"Insertion Successful", Toast.LENGTH_LONG).show();
            }
        });

        /*
         * On click listener for logout button- returns to login activity
         */
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Toast.makeText(getApplicationContext(),"Log Out Successful", Toast.LENGTH_LONG).show();
            }
        });

    }
}