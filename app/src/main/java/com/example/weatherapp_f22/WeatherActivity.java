package com.example.weatherapp_f22;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class WeatherActivity extends AppCompatActivity implements NetworkingService.NetworkingListener {

    NetworkingService networkingService;
    TextView tempText;
    TextView humText;
    TextView desText;
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        tempText = findViewById(R.id.temp);
        humText = findViewById(R.id.humidity);
        desText = findViewById(R.id.des);
        img = findViewById(R.id.img);

        if( getIntent().hasExtra("city")){
           City city =  getIntent().getParcelableExtra("city");
            networkingService =( (MyApp)getApplication()).networkingService;
            networkingService.listener = this;
            networkingService.getWeather(city.city +","+ city.country);
        }
    }

    @Override
    public void connectionISDoneWithResult(String json) {

        WeatherData weatherData = JsonService.getWeatherFromJsonString(json);
        tempText.setText(weatherData.temp + "F");
        humText.setText(weatherData.humidity + "");
        desText.setText(weatherData.description+ "");
        networkingService.getIcon(weatherData.icon);

    }

    @Override
    public void weatherIconDownoaded(Bitmap weatherIcon) {
        img.setImageBitmap(weatherIcon);
    }
}