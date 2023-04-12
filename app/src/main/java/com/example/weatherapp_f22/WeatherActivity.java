package com.example.weatherapp_f22;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class WeatherActivity extends AppCompatActivity implements
        NetworkingManager.NetworkingCallBackInterface
        {
    TextView tempText;
    TextView humText;
    TextView desText;
    ImageView img;

    WeatherData weatherData ;
    NetworkingManager networkingManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        tempText = findViewById(R.id.temp);
        humText = findViewById(R.id.humidity);
        desText = findViewById(R.id.des);
        img = findViewById(R.id.img);
        networkingManager = ((MyApp)getApplication()).networkingManager;
        networkingManager.listener = this;
        City city = (City) getIntent().getParcelableExtra("city");
        networkingManager.getWeather(city);
        this.setTitle( " Weather in "+ city.city );
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public void networkingManagerCompleteWithJsonString(String jsonString) {
        weatherData =    JsonManager.fromStringToWeathedData(jsonString);
        tempText.setText(weatherData.temp + "");
        humText.setText(weatherData.humidity + "");
        desText.setText(weatherData.description);
        networkingManager.getImageData(weatherData.icon);
    }

            @Override
            public void newtworkingMangerCompleteWithWeatherIcon(Bitmap image) {
                img.setImageBitmap(image);
            }
        }