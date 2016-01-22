package com.inlocomedia.weather.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.inlocomedia.weather.R;
import com.inlocomedia.weather.WeatherDetailActivity;
import com.inlocomedia.weather.models.City;
import com.inlocomedia.weather.models.Weather;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by augusto on 20/01/16.
 */
public class CityListAdapter extends RecyclerView.Adapter<CityListAdapter.ViewHolder> {

    private List<City> cities;

    public CityListAdapter(ArrayList<City> cities){
        this.cities = cities;
    }


    @Override
    public CityListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        //Inicializa o layout de cada item da RecyclerView com o layout city_list_item.xml
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_list_item, parent, false);
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(CityListAdapter.ViewHolder holder, int position) {

        //Pega um objeto CITY para colocar as informações no viewholder

        final City city = cities.get(position);
        holder.cityName.setText(city.getName());


        //Associa a ação de cada item do adapter
        holder.itemView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        /*
                        Cada item tem um intent associado, é necessário colocar as informações
                        que devem ser passadas para a próxima tela.
                         */

                        Intent i = new Intent(view.getContext(), WeatherDetailActivity.class);
                        i.putExtra("CITY_NAME", city.getName());
                        i.putExtra("CITY_MAXTEMP", city.getMaxTemperature());
                        i.putExtra("CITY_MINTEMP", city.getMinTemperature());
                        Weather weather = city.getWeather();
                        i.putExtra("WEATHER_DESCRIPTION", weather.getDescription());
                        i.putExtra("WEATHER_ICON", weather.getIcon());
                        view.getContext().startActivity(i);
                    }
                }
        );
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView cityName;

        public ViewHolder(View v){
            super(v);
            this.cityName = (TextView) v.findViewById(R.id.cityName);
        }

    }
}
