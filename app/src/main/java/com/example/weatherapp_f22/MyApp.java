package com.example.weatherapp_f22;

import android.app.Application;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class MyApp extends Application {
    NetworkingService networkingService = new NetworkingService();

}
