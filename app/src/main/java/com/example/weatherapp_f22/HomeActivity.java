package com.example.weatherapp_f22;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Arrays;

public class HomeActivity extends AppCompatActivity
        implements DBManager.DatabaseListener,
        CitiesRecyclerViewAdapter.CitiesClickListener {


    RecyclerView list;
    CitiesRecyclerViewAdapter adapter;
    ArrayList<City> citiesArray = new ArrayList<>(0);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        list = findViewById(R.id.homeCitiesList);
        ((MyApp)getApplication()).dbManager.listener = this;
        ((MyApp)getApplication()).dbManager.getDB(this);
        adapter = new CitiesRecyclerViewAdapter(citiesArray,this);
        adapter.listener = this;

        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);



        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            public boolean onMove(RecyclerView recyclerView,
                                  RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

                return true;// true if moved, false otherwise
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                //Remove swiped item from list and notify the RecyclerView
                ((MyApp)getApplication()).dbManager.deleteCityAsync(citiesArray.get(viewHolder.getAdapterPosition()));

            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(list);
    }


    @Override
    protected void onResume() {
        super.onResume();
        adapter.listener = this;
        ((MyApp)getApplication()).dbManager.listener = this;
        ((MyApp)getApplication()).dbManager.getAllCitiesAcync();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
      switch (item.getItemId()){
          case R.id.addNewCity:
              Intent i = new Intent(this, MainActivity.class);
              startActivity(i);
              break;
      }
      return true;
    }


    @Override
    public void insertingCityCompleted() {

    }

    @Override
    public void gettingAllCityCompleted(City[] l) {
        citiesArray = new ArrayList<>(Arrays.asList(l));
        adapter.list = citiesArray;
        adapter.notifyDataSetChanged();
       // adapter = new CitiesRecyclerViewAdapter(citiesArray,this);

    }

    @Override
    public void deletingCityCompleted() {
        ((MyApp)getApplication()).dbManager.getAllCitiesAcync();
    }

    @Override
    public void onCityClicked(City selectedCity) {
        // go to weather activity
        Intent intent = new Intent(this,WeatherActivity.class);
        intent.putExtra("city",selectedCity);
        startActivity(intent);
    }
}