package com.example.weatherapp_f22;

public class WeatherData {

    double temp;
    String description;
    String icon;
    int humidity;

    public WeatherData() {
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public WeatherData(double temp, String description, String icon, int humidity) {
        this.temp = temp;
        this.description = description;
        this.icon = icon;
        this.humidity = humidity;
    }
}
