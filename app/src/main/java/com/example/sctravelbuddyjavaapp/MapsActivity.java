/**
 * Smart Cities Travel Buddy
 * Created by Sriram, Simhendra and Anish, 2021
 * Course- Mobile Application Development, 6th sem, RVCE
 */

package com.example.sctravelbuddyjavaapp;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sctravelbuddyjavaapp.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Activity class for a city-specific attractions page with a map
 */
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private RecyclerView recyclerView;
    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private Geocoder geocoder;
    private String cityname="";
    private AttractionAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    public TextView temp;
    public TextView city;
    public TextView weatherinfo;

    // Description of a city (eg- Garden City of India)
    String description = "";

    ArrayList<Attraction> attractions = new ArrayList<>();

    DatabaseManager dbManager;


    /**
     * Method called when activity is launched
     * @param savedInstanceState saved state information
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
        geocoder = new Geocoder(this);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new AttractionAdapter(attractions);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        temp = (TextView) findViewById(R.id.temp);
        city = (TextView) findViewById(R.id.city);
        weatherinfo = (TextView) findViewById(R.id.weatherinfo);


        // gets city name from intent passed from SelectorActivity
        if(getIntent().hasExtra("CITY_NAME")){
            String city_selected = getIntent().getExtras().getString("CITY_NAME");
            this.cityname = city_selected;
            city.setText(city_selected);

            // fetches db data using a thread
            new Thread(new Runnable() {
                @Override
                public void run() {
                    dbManager = new DatabaseManager(getApplicationContext());
                    try {
                        dbManager.open();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Cursor cursor = dbManager.fetch(cityname);

                    int ctr = 0;
                    attractions.clear();
                    if (cursor.moveToFirst()){
                        while (!cursor.isAfterLast()) {
                            ctr += 1;
                            String place = cursor.getString(1);
                            String address = cursor.getString(2);
                            String description = cursor.getString(3);
                            String type = cursor.getString(4);
                            int type_num;
                            if (type.equalsIgnoreCase("tourist"))
                                type_num = 1;

                            else if (type.equalsIgnoreCase("hotel"))
                                type_num = 2;

                            else
                                type_num = 3;

                            attractions.add(new Attraction(place, address, description, type_num));

                            cursor.moveToNext();
                        }
                    }
                    System.out.println("After query "+ctr);

                    cursor.close();
                    dbManager.close();
                }

            }).start();


            // OpenWeatherMaps API's URL and API Key
            String API_KEY = "1aec76a6def1c20118399b55e15a7f56";
            String url = ("https://api.openweathermap.org/data/2.5/weather?q=" + city_selected + "&units=metric&appid=" + API_KEY);

            // Gets weather data from openweather maps api and sets this data in this thread
            new Thread(new Runnable(){
                @Override
                public void run() {
                    try {
                        URL url_obj = new URL(url);
                        URLConnection conn = url_obj.openConnection();
                        InputStream is = conn.getInputStream();
                        Scanner s = new Scanner(is).useDelimiter("\\A");
                        String result = s.hasNext() ? s.next() : "";

                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            System.out.println(jsonObject);
                            String temp_str = jsonObject.getJSONObject("main").getString("temp");
                            temp_str = temp_str + " C";
                            temp.setText(temp_str);

                            String weather_str = jsonObject.getJSONArray("weather").getJSONObject(0).getString("main");
                            weatherinfo.setText(weather_str);

                        }catch (JSONException err){
                            Log.d("Error", err.toString());
                        }
                    }
                    catch (Exception e) {
                        Log.d("Error", e.toString());
                    }
                }
            }).start();

        }


    }


    // Method called when map loads
    @Override
    public void onMapReady(GoogleMap googleMap) {
        System.out.println("OnMapReady");
        this.mMap = googleMap;
        this.mMap.getUiSettings().setZoomControlsEnabled(true);
        this.mMap.getUiSettings().setZoomGesturesEnabled(true);
        float[] types={BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_YELLOW,BitmapDescriptorFactory.HUE_GREEN};


        try {
            Address city = geocoder.getFromLocationName(this.cityname, 1).get(0);
            LatLng citycoordinates = new LatLng(city.getLatitude(),city.getLongitude());
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(citycoordinates,9.5f));

            LatLng temp;
            Attraction tempattr;


            // Iterate through the list of attractions and add markers on the map with different colors
            // corresponding to attraction types defined in the Attraction class
            for(int i=0;i<attractions.size();i++) {
                tempattr = attractions.get(i);
                Address attraction = geocoder.getFromLocationName(tempattr.getAddress(), 1).get(0);
                temp = new LatLng(attraction.getLatitude(),attraction.getLongitude());
                mMap.addMarker(new MarkerOptions().position(temp).icon(BitmapDescriptorFactory.defaultMarker(types[tempattr.getType()-1])).snippet(tempattr.getDescription()).title(tempattr.getName()));

            }

            // Sets on click listener for attraction card items in the recycler view
            adapter.setOnItemClickListener(new AttractionAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Toast t = Toast.makeText(getApplicationContext(),
                            "Attraction Item shown on Map",
                            Toast.LENGTH_SHORT);
                    t.show();

                    String a = attractions.get(position).getAddress();
                    try {
                        Address adr = geocoder.getFromLocationName(a, 1).get(0);
                        LatLng coordinates = new LatLng(adr.getLatitude(),adr.getLongitude());
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinates,10.5f));
                    }
                    catch (IOException f)
                    {
                        f.printStackTrace();
                    }

                }
            });


        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}