package com.inlocomedia.weather;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by augusto on 20/01/16.
 */
public class WeatherApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Fresco.initialize(WeatherApplication.this);

    }
}
