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

public class NetworkingManager {

    interface NetworkingCallBackInterface{
        void networkingManagerCompleteWithJsonString(String jsonString);
        void newtworkingMangerCompleteWithWeatherIcon(Bitmap image);
    }

    NetworkingCallBackInterface listener;


    // cities url
    String citiesAPI = "http://gd.geobytes.com/AutoCompleteCity?&q=";

    void getCities(String query){
       getDatafromURL(citiesAPI+query);
    }

    void  getWeather(City city){
        String weatherURL1 = "https://api.openweathermap.org/data/2.5/weather?q=";
        String weatherURL2 = "&appid=071c3ffca10be01d334505630d2c1a9c";
        getDatafromURL(weatherURL1+city.city+","+city.country+weatherURL2);
    }



    private void getDatafromURL(String urlString){
        MultithreadingManager.executorService.execute(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection urlConnection = null;
                // the code here will run in a background thread
                // do networking here
                try {
                    URL url = new URL(urlString);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setRequestProperty("content-Type","application/json");

                    InputStream in = urlConnection.getInputStream();
                    InputStreamReader reader = new InputStreamReader(in);
                    int value = 0;
                    String jsonString = "";
                    while ((value = reader.read()) != -1){
                        jsonString += (char)value;
                    }

                    final String json = jsonString;
                    MultithreadingManager.handler.post(new Runnable() {
                        @Override
                        public void run() {
                            // the code here will run in main thread
                            // I in main thread
                            listener.networkingManagerCompleteWithJsonString(json);
                        }
                    });

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    urlConnection.disconnect();
                }


            }
        });
    }




    public void getImageData(String icon){
        String iconurl = "https://openweathermap.org/img/wn/"+icon+"@2x.png";
        MultithreadingManager.executorService.execute(new Runnable() {
            @Override
            public void run() {

                try {
                    URL url = new URL(iconurl);
                    Bitmap bitmap = BitmapFactory.decodeStream((InputStream)url.getContent()) ;
                    MultithreadingManager.handler.post(new Runnable() {
                        @Override
                        public void run() {
                            listener.newtworkingMangerCompleteWithWeatherIcon(bitmap);
                        }
                    });

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

}
