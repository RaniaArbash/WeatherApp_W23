package com.example.weatherapp_f22;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultithreadingManager {
   static ExecutorService executorService =
            Executors.newFixedThreadPool(4);

  static   Handler handler = new Handler(Looper.getMainLooper());

}
