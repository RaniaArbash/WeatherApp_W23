package com.example.weatherapp_f22;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements
        NetworkingService.NetworkingListener {
    ArrayList<City> cities = new ArrayList<>(0);
    NetworkingService networkingService = new NetworkingService();
    RecyclerView rv;
    CitiesRecyclerViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        networkingService.listener = this;
        rv = findViewById(R.id.citiesList);
        adapter = new CitiesRecyclerViewAdapter(cities,this);

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.weather_menu,menu);
        MenuItem searchViewmenue = menu.findItem(R.id.searchbar);

        SearchView searchView = (SearchView) searchViewmenue.getActionView();

        //String cityQuery = searchView.getQuery().toString();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("weather app",  "Query " + query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("weather app", "changes " + newText);
                if (newText.length() >= 3){
                    // get api result
                    networkingService.connect(newText);
                }
                return false;
            }
        });


        return true;
    }


    @Override
    public void citiesAPIISDoneWithResult(String json) {
        Log.d("weather app " , json);

      cities = JsonService.getCitiesFromJsonString(json);
      adapter.list = cities;
      adapter.notifyDataSetChanged();



    }
}