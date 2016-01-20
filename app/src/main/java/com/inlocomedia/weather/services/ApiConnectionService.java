package com.inlocomedia.weather.services;

import com.inlocomedia.weather.models.City;
import com.inlocomedia.weather.models.Weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by augusto on 20/01/16.
 */
public class ApiConnectionService {
    final private static String WEATHER_API_URL = "openweathermap.org";

    final private static String WEATHER_APP_ID = "d5e5e7bf0036493556227d17d41219bd";

    final private static int QUERIED_CITIES = 15;

    private static OkHttpClient okHttpClient;

    public OkHttpClient getClient(){
        if(this.okHttpClient == null){
            okHttpClient = new OkHttpClient();
        }
        return this.okHttpClient;
    }


    public List<City> retrieveCities(String latitude, String longitude){
        HttpUrl httpUrl = new HttpUrl.Builder().scheme("http").host(WEATHER_API_URL)
                .addPathSegment("data")
                .addPathSegment("2.5")
                .addPathSegment("find")
                .addQueryParameter("lat", latitude)
                .addQueryParameter("lon", longitude)
                .addQueryParameter("cnt", String.valueOf(QUERIED_CITIES))
                .addQueryParameter("APPID", WEATHER_APP_ID)
                .addQueryParameter("units", "metric")
                .build();

        Request request = new Request.Builder().url(httpUrl)
                .build();

        try {
            JSONArray cities = new JSONObject(getClient().newCall(request).execute().body().string()).getJSONArray("list");

            List<City> cityList = new ArrayList<>();

            for(int i = 0; i < cities.length(); i++){
                City city = new City();
                parseJsonIntoCity(cities.getJSONObject(i), city);
                if(city != null){
                    cityList.add(city);
                }

            }

            return cityList;
        } catch (IOException|JSONException e) {
            return null;
        }


    }

    public City parseJsonIntoCity(JSONObject node, City placeholder){

        try {
            placeholder.setId(node.getString("id"));
            placeholder.setName(node.getString("name"));
            placeholder.setMaxTemperature(node.getJSONObject("main").getString("temp_max"));
            placeholder.setMinTemperature(node.getJSONObject("main").getString("temp_min"));
            JSONObject weatherNode = node.getJSONArray("weather").getJSONObject(0);
            Weather placeholderWeather = new Weather();
            placeholderWeather.setDescription(weatherNode.getString("description"));
            placeholderWeather.setName(weatherNode.getString("name"));
            placeholderWeather.setIcon(weatherNode.getString("icon"));
            placeholder.setWeather(placeholderWeather);
        } catch (JSONException e) {
            return null;
        }

        return placeholder;
    }
}
