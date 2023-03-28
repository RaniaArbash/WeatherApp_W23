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
        implements
        CitiesRecyclerViewAdapter.CitiesClickListener {


    RecyclerView list;
    CitiesRecyclerViewAdapter adapter;
    ArrayList<City> citiesArray = new ArrayList<>(0);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        list = findViewById(R.id.homeCitiesList);
        adapter = new CitiesRecyclerViewAdapter(citiesArray,this);
        adapter.listener = this;

        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);

    }


    @Override
    protected void onResume() {
        super.onResume();
        adapter.listener = this;

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
    public void onCityClicked(City selectedCity) {
        // go to weather activity
        Intent intent = new Intent(this,WeatherActivity.class);
        intent.putExtra("city",selectedCity);
        startActivity(intent);
    }
}