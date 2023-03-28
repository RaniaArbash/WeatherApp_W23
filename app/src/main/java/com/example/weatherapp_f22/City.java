package com.example.weatherapp_f22;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class City implements Parcelable {

    String city;
    String country;



    public City() {
    }

    public City( String city, String country) {

        this.city = city;
        this.country = country;
    }




    City(String fullCityString){
        // Toronto, On, Canada ==> Toronto Canada
        String state = "";
        char[] list = fullCityString.toCharArray();
        for (int i = 0 ; i<list.length; i++){
            if (list[i] == ','){

                city = fullCityString.substring(0, i);
                i = i + 1;

                for (int j = i; j < list.length ; j++) {
                    if (list[j] == ',') {

                        country = fullCityString.substring(j + 2, fullCityString.length());
                        break;
                    }

                }
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
