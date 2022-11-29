package com.example.weatherapp_f22;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CitiesRecyclerViewAdapter extends RecyclerView.Adapter<CitiesRecyclerViewAdapter.CitiesViewHolder> {

    ArrayList<City> list;
    Context context;

    interface CitiesClickListener{
        void onCityClicked(City selectedCity);
    }

    CitiesClickListener listener;


    public CitiesRecyclerViewAdapter(ArrayList<City> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public CitiesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater myInflater = LayoutInflater.from(context);
        View view = myInflater.inflate(R.layout.city_row_layout,parent,false);

        return new CitiesViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull CitiesViewHolder holder, int position) {
        holder.city.setText(list.get(position).city);
        holder.country.setText(list.get(position).country);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CitiesViewHolder extends RecyclerView.ViewHolder{
        TextView city;
        TextView country;
        public CitiesViewHolder(@NonNull View itemView) {
            super(itemView);
            city = itemView.findViewById(R.id.cityname);
            country = itemView.findViewById(R.id.countryname);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //
                    listener.onCityClicked(list.get(getAdapterPosition()));
                }
            });

        }
    }
}
