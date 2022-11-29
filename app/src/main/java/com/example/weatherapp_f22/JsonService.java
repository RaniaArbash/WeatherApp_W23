package com.example.weatherapp_f22;

import org.json.JSONArray;
import org.json.JSONException;

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

}
