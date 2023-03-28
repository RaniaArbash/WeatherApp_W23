package com.example.weatherapp_f22;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonService {


    public static ArrayList<City> getListOfCities (String json){
        ArrayList< City> cities = new ArrayList<>(0);

        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0 ; i< jsonArray.length();i++){
                cities.add( new City(jsonArray.getString(i)));
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return cities;
    }


    public static WeatherData getWeatherInfo(String json){
        WeatherData weatherData = new WeatherData();

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("weather");
            weatherData.description = jsonArray.getJSONObject(0).getString("main");
            weatherData.icon = jsonArray.getJSONObject(0).getString("icon");

            weatherData.temp = jsonObject.getJSONObject("main").getDouble("temp");
            weatherData.humidity = jsonObject.getJSONObject("main").getInt("humidity");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return weatherData;
    }

}
