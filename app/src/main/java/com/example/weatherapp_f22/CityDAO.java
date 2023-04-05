package com.example.weatherapp_f22;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CityDAO {

    @Insert
    void insertNewCity(City c);

    @Query("select * from City")
    List<City> getAllCitiesFromDB();

    @Query("select * from City where city_name like :text")
    List<City> getAllCitiesStartWith(String text);

    @Delete
    void deleteCity(City c);


}
