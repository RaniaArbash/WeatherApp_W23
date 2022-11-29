package com.example.weatherapp_f22;

public class City {

    String city;
    String country;


    City(String fullCityString){
        char[] list = fullCityString.toCharArray();
        for (int i = 0 ; i<list.length; i++){
            if (list[i] == ','){
                city = fullCityString.substring(0, i );
                country = fullCityString.substring(i + 1 , fullCityString.length());
                break;
            }
        }
    }


}
