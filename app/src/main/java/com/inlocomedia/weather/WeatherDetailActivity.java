package com.inlocomedia.weather;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

public class WeatherDetailActivity extends AppCompatActivity {
    TextView weatherDescription;
    TextView cityName;
    TextView minTemp;
    TextView maxTemp;
    SimpleDraweeView weatherIcon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_detail);

        weatherDescription= (TextView) findViewById(R.id.weatherDescription);
        cityName = (TextView) findViewById(R.id.cityName);
        minTemp = (TextView) findViewById(R.id.maxTemp);
        maxTemp = (TextView) findViewById(R.id.minTemp);

        weatherIcon  = (SimpleDraweeView) findViewById(R.id.weatherIcon);

        setWeatherContent();
    }

    private void setWeatherContent(){
        Bundle bundle = getIntent().getExtras();
        weatherDescription.setText(bundle.getString("WEATHER_DESCRIPTION"));
        cityName.setText(bundle.getString("CITY_NAME"));
        minTemp.setText(bundle.getString("CITY_MINTEMP"));
        maxTemp.setText(bundle.getString("CITY_MAXTEMP"));

        Uri uri = Uri.parse("http://openweathermap.org/img/w/" + bundle.getString("WEATHER_ICON") + ".png");
        weatherIcon.setImageURI(uri);


    }
}
