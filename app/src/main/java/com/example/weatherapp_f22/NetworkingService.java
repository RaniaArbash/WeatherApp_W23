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
    String weatherURL1 = "https://api.openweathermap.org/data/2.5/weather?q=";
    String weatherURL2 = "&appid=071c3ffca10be01d334505630d2c1a9c";

    // call back // listeners
    interface NetworkingListener{
         void connectionISDoneWithResult(String json);
         void weatherIconDownoaded(Bitmap img);
    }

    public NetworkingListener listener;

    String cityAPIURL = "http://gd.geobytes.com/AutoCompleteCity?&q=";

    ExecutorService networkingExecutorService = Executors.newFixedThreadPool(4);
    Handler newtworkingHandler = new Handler(Looper.getMainLooper());

    public void getCites(String q){
        String url = cityAPIURL + q;
        connect(url);
    }

    public void getWeather(String city){
        String url = weatherURL1 + city +weatherURL2;
        connect(url);
    }

    public void getIcon(String icon){
        String urlstring = "http://openweathermap.org/img/wn/"+icon+"@2x.png";
        networkingExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(urlstring);
                    InputStream in = (InputStream) url.getContent();
                    Bitmap imageData = BitmapFactory.decodeStream(in);
                    newtworkingHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            listener.weatherIconDownoaded(imageData);
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

    public void connect(String urlstring){// general function for all connection
        networkingExecutorService.execute(new Runnable() {
            HttpURLConnection urlConnection;
            @Override
            public void run() {
                // run in background thread
                try {
                    URL url = new URL(urlstring);
                    urlConnection  = (HttpURLConnection)url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setRequestProperty("Content-Type", "application/json");

                    InputStream in = urlConnection.getInputStream();
                    InputStreamReader reader = new InputStreamReader(in);
                    int value = 0;
                    String jsonString = "";
                    while ( (value = reader.read()) != -1 ){
                        char current = (char)value;
                        jsonString+= current;
                    }

                    final String json = jsonString;
                    // we need to comeback to main thread when done.
                    newtworkingHandler.post(new Runnable() {
                        @Override
                        public void run() {
                           listener.connectionISDoneWithResult(json);
                        }
                    });

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                finally {
                    urlConnection.disconnect();
                }


            }
        });


    }
}
