package com.example.weatherapp_f22;


import android.content.Context;

import androidx.room.Room;

import java.util.List;

public class DatabaseManager {

    interface DatabaseCallBackInterface{
        void databaseManagerCompleteWithListOfCities(List<City> dbList);
        void databaseManagerCompleteDeletingCity();
    }

    DatabaseCallBackInterface listener;
    /*To prevent queries from blocking the UI,
    Room does not allow database access on the main thread.*/
    static CitiesDatabase citiesDatabase;

    public static CitiesDatabase getDB(Context context){
        if (citiesDatabase == null){
          return citiesDatabase = Room.databaseBuilder(context,
                    CitiesDatabase.class, "cityDB").build();
        } else
            return  citiesDatabase;
    }


    public void insertNewCity(City newCity){

        MultithreadingManager.executorService.execute(new Runnable() {
            @Override
            public void run() {
                DatabaseManager.citiesDatabase.getCityDao().insertNewCity(newCity);
            }
        });
    }

    public void getAllCities(){
        MultithreadingManager.executorService.execute(new Runnable() {
            @Override
            public void run() {
               List<City> list = DatabaseManager.citiesDatabase.getCityDao().getAllCitiesFromDB();
                MultithreadingManager.handler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.databaseManagerCompleteWithListOfCities(list);
                    }
                });

            }
        });
    }


    public void deleteCity(City todelete){
        MultithreadingManager.executorService.execute(new Runnable() {
            @Override
            public void run() {
                DatabaseManager.citiesDatabase.getCityDao().deleteCity(todelete);
                MultithreadingManager.handler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.databaseManagerCompleteDeletingCity();
                    }
                });
            }
        });
    }

}
