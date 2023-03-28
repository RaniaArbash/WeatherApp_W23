package com.example.weatherapp_f22;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class WeatherActivity extends AppCompatActivity implements NetworkingService.NetworkingCallBack {
    NetworkingService networkingService;
    TextView tempText;
    TextView humText;
    TextView desText;
    ImageView img;

    WeatherData weatherData ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        networkingService =( (MyApp)getApplication()).networkingService;

        tempText = findViewById(R.id.temp);
        humText = findViewById(R.id.humidity);
        desText = findViewById(R.id.des);
        img = findViewById(R.id.img);
        networkingService.listener = this;

        City city = (City) getIntent().getParcelableExtra("city");
        networkingService.getWeather(city);

    }

    @Override
    protected void onResume() {
        super.onResume();
        networkingService.listener = this;
    }

    @Override
    public void networkingFinishWithJsonString(String json) {
        Log.d("weather", json);
        weatherData = JsonService.getWeatherInfo(json);
        tempText.setText("" + weatherData.temp);
        humText.setText(""+ weatherData.humidity);
        desText.setText(weatherData.description);
    }
}