package com.example.weatherapp_f22;

import android.os.Parcel;
import android.os.Parcelable;

public class City implements Parcelable {

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


    protected City(Parcel in) {
        city = in.readString();
        country = in.readString();
    }

    public static final Creator<City> CREATOR = new Creator<City>() {
        @Override
        public City createFromParcel(Parcel in) {
            return new City(in);
        }

        @Override
        public City[] newArray(int size) {
            return new City[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(city);
        parcel.writeString(country);
    }
}
