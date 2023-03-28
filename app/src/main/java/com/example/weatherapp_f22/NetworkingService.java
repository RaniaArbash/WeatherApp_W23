package com.example.weatherapp_f22;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NetworkingService {


    interface NetworkingCallBack{
        void networkingFinishWithJsonString(String json);
    }

    Handler networkingHandler = new Handler(Looper.getMainLooper());
    NetworkingCallBack listener;
    String weatherURL1 = "https://api.openweathermap.org/data/2.5/weather?q=";
    String weatherURL2 = "&appid=071c3ffca10be01d334505630d2c1a9c";
    String cityAPIURL = "http://gd.geobytes.com/AutoCompleteCity?&q=";
    ExecutorService executorService;
    NetworkingService(){
        executorService = Executors.newFixedThreadPool(4);

    }

    private void getData(String urlstring){
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(urlstring);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setRequestProperty("content-Type","application/json");

                    InputStream in =  urlConnection.getInputStream();
                    InputStreamReader reader = new InputStreamReader(in);
                    int value = 0;
                    String jsonString = "";
                    while ((value = reader.read()) != -1){
                        jsonString += (char)value;
                    }
                    final String json = jsonString;
                    // go back to main thread in order to call the listener
                    networkingHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            listener.networkingFinishWithJsonString(json);
                        }
                    });

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // run in background thread

            }
        });

    }


    public void getWeather (City city){

        //  HttpURLConnection urlConnection;
        // send a http request to cities api
        // " ON, Canada"
        getData(weatherURL1+city.city+","+city.country+weatherURL2);



    }

    public void getCities (String query){

      //  HttpURLConnection urlConnection;
        // send a http request to cities api
        getData(cityAPIURL+query);



    }

}
