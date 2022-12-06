package com.example.weatherapp_f22;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface CitiesDao {

    @Insert
    void insertNewCity(City c);

    @Query("select * from City")
    City[] getAllDBCities();

    @Delete
    void deleteCity(City c);

    @Query("select * from City where lower(city) like :sub ")
    City[] getAllDBCitiesStartWith(String sub);
}
