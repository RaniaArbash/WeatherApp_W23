package com.example.weatherapp_f22;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonManager {

    static ArrayList<City> fromJsonStringToList(String jsonString){
        ArrayList<City> cities = new ArrayList<>(0);

        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0 ;i < jsonArray.length();i++){
              cities.add(new City(jsonArray.getString(i)));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return cities;
    }

    static WeatherData fromStringToWeathedData(String json){
        WeatherData weatherData = new WeatherData();
        try {
            JSONObject rootJsonObject = new JSONObject(json);
            JSONObject mainJsonObject = rootJsonObject.getJSONObject("main");
            weatherData.temp = mainJsonObject.getDouble("temp");
            weatherData.humidity = mainJsonObject.getInt("humidity");

            JSONArray weatherArray = rootJsonObject.getJSONArray("weather");
           weatherData.description = weatherArray.getJSONObject(0).getString("description");
            weatherData.icon = weatherArray.getJSONObject(0).getString("icon");


        } catch (JSONException e) {
            e.printStackTrace();
        }


        return weatherData;
    }
}
