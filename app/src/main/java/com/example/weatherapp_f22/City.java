package com.example.weatherapp_f22;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class City implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    int id;
    String city;
    String country;



    public City() {
    }

    public City(int id, String city, String country) {
        this.id = id;
        this.city = city;
        this.country = country;
    }




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
        id = in.readInt();
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
        parcel.writeInt(id);
        parcel.writeString(city);
        parcel.writeString(country);
    }
}
