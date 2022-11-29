package com.example.weatherapp_f22;

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

    // call back // listeners
    interface NetworkingListener{
         void citiesAPIISDoneWithResult(String json);
    }

    public NetworkingListener listener;

    String cityAPIURL = "http://gd.geobytes.com/AutoCompleteCity?&q=";

    ExecutorService networkingExecutorService = Executors.newFixedThreadPool(4);
    Handler newtworkingHandler = new Handler(Looper.getMainLooper());


    public void connect(String q){
        networkingExecutorService.execute(new Runnable() {
            HttpURLConnection urlConnection;
            @Override
            public void run() {
                // run in background thread
                try {
                    URL url = new URL(cityAPIURL+q);
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
                           listener.citiesAPIISDoneWithResult(json);
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
