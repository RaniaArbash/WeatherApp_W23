package com.example.weatherapp_f22;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements
       CitiesRecyclerViewAdapter.CitiesClickListener, NetworkingService.NetworkingCallBack

{

    NetworkingService networkingService;
    ArrayList<City> cities = new ArrayList<>(0);
    RecyclerView rv;
    CitiesRecyclerViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        networkingService =( (MyApp)getApplication()).networkingService;
        networkingService.listener = this;
        rv = findViewById(R.id.citiesList);
        adapter = new CitiesRecyclerViewAdapter(cities,this);
        adapter.listener = this;
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.weather_menu,menu);
        MenuItem searchViewmenue = menu.findItem(R.id.searchbar);

        SearchView searchView = (SearchView) searchViewmenue.getActionView();

        //String cityQuery = searchView.getQuery().toString()

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("weather app",  "Query " + query);
                /// search for the city in my networking class
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("weather app", "changes " + newText);
                if (newText.length() >= 3){

                    networkingService. getCities(newText);

                }
                else {
                    adapter.list = new ArrayList<>(0);
                    adapter.notifyDataSetChanged();
                }
                return false;
            }
        });


        return true;
    }


    @Override
    protected void onResume() {
        super.onResume();
        networkingService.listener = this;
    }
    @Override
    public void onCityClicked(City selectedCity) {

        Intent intent = new Intent(this,WeatherActivity.class);
        intent.putExtra("city",selectedCity);
        startActivity(intent);

    }


    @Override
    public void networkingFinishWithJsonString(String json) {
        Log.d("weather",json);
        cities = JsonService.getListOfCities(json);
        adapter.list = cities;
        adapter.notifyDataSetChanged();
    }
}