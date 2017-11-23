package com.example.openweather.api.model;

import android.support.annotation.IntDef;
import android.support.annotation.StringDef;

import com.google.gson.annotations.SerializedName;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

/**
 * Created by kumar on 11/20/17.
 */

public class WeatherResponse {

    @SerializedName("weather")
    List<Weather> weatherList;
    Wind wind;
    @SerializedName("main")
    Main main;
    String name;

    public Main getMain() {
        return main;
    }

    public List<Weather> getWeatherList() {
        return weatherList;
    }

    public String getName() {
        return name;
    }

    public class Weather {
        String id;
        String main;
        String description;
        String icon;

        public String getId() {
            return id;
        }

        public String getDescription() {
            return description;
        }

        public String getIcon() {
            return icon;
        }
    }

    public class Wind {
        float speed;
        float deg;
    }

    public class Main {
        float temp;
        float pressure;
        float humidity;

        public float getHumidity() {
            return humidity;
        }

        public float getPressure() {
            return pressure;
        }

        public float getTemp() {
            return temp;
        }
    }


    @StringDef({WeatherUnit.TYPE_CELCIUS, WeatherUnit.TYPE_FARENHEIT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface WeatherUnit {
        String TYPE_CELCIUS = "Metric";
        String TYPE_FARENHEIT = "Imperial";
    }

}
