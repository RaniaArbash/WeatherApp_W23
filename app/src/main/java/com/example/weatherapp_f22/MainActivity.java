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
        NetworkingService.NetworkingListener ,
        CitiesRecyclerViewAdapter.CitiesClickListener ,
        DBManager.DatabaseListener
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
        ((MyApp)getApplication()).dbManager.getDB(this);
        ((MyApp)getApplication()).dbManager.listener = this;

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
                    networkingService.getCites(newText);
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
    public void connectionISDoneWithResult(String json) {
        Log.d("weather app " , json);

      cities = JsonService.getCitiesFromJsonString(json);
      adapter.list = cities;
      adapter.notifyDataSetChanged();



    }

    @Override
    public void weatherIconDownoaded(Bitmap img) {

    }

    @Override
    public void onCityClicked(City selectedCity) {

        showAlert(selectedCity);
    }

    void showAlert(City city){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to save "+ city.city+ " in your DB ??");
        builder.setNegativeButton("No",null);
        builder.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // save the city into db.
                ((MyApp)getApplication()).dbManager.insertNewCityAsync(city);
            }
        });
        builder.create().show();

    }

    @Override
    public void insertingCityCompleted() {
        finish();
    }

    @Override
    public void gettingAllCityCompleted(City[] l) {

    }

    @Override
    public void deletingCityCompleted() {

    }
}