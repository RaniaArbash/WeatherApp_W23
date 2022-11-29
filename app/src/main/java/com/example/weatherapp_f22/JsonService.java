package com.example.weatherapp_f22;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonService {


    public static ArrayList<City> getCitiesFromJsonString(String json){
        ArrayList<City> cities = new ArrayList<>(0);

        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0 ; i < jsonArray.length(); i++){
                String city = jsonArray.getString(i);
                City cityObject = new City(city);
                cities.add(cityObject);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return cities;
    }


    public static WeatherData getWeatherFromJsonString(String json){
        WeatherData weatherData = new WeatherData();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray weatherJsonArray = jsonObject.getJSONArray("weather");
            weatherData.setDescription(weatherJsonArray.getJSONObject(0).getString("main"));
            weatherData.setIcon(weatherJsonArray.getJSONObject(0).getString("icon"));
            weatherData.setTemp(jsonObject.getJSONObject("main").getDouble("temp"));
            weatherData.setHumidity(jsonObject.getJSONObject("main").getInt("humidity"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    return weatherData;
    }

}
