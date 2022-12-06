package com.example.weatherapp_f22;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.room.Room;

public class DBManager {

    interface DatabaseListener{
        void insertingCityCompleted();
        void gettingAllCityCompleted(City[] l);
        void deletingCityCompleted();
    }

    CitiesDatabase db;
    DatabaseListener listener;

    Handler handler = new Handler(Looper.getMainLooper());

    CitiesDatabase getDB(Context context){
        if (db == null)
              db = Room.databaseBuilder(context,
                CitiesDatabase.class, "database-cities").build();
        return db;
    }


    void insertNewCityAsync(City c){
        MyApp.executorService.execute(new Runnable() {
            @Override
            public void run() {
                db.getDao().insertNewCity(c);
                // I am in background thread.
                // I need to notify main thread
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        // will run in main thread
                        listener.insertingCityCompleted();
                    }
                });

            }
        });
    }

    void getAllCitiesAcync(){
        MyApp.executorService.execute(new Runnable() {
            @Override
            public void run() {
               City[] list =  db.getDao().getAllDBCities();
                // I am in background thread.
                // I need to notify main thread
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        // will run in main thread
                        listener.gettingAllCityCompleted(list);
                    }
                });

            }
        });
    }

    void deleteCityAsync(City c){
        MyApp.executorService.execute(new Runnable() {
            @Override
            public void run() {
                db.getDao().deleteCity(c);
                // I am in background thread.
                // I need to notify main thread
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        // will run in main thread
                        listener.deletingCityCompleted();
                    }
                });

            }
        });
    }
}
