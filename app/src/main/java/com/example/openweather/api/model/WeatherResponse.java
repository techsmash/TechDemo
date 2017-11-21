package com.example.openweather.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by kumar on 11/20/17.
 */

public class WeatherResponse {

    @SerializedName("weather")
    List<Weather> weatherList;
    Wind wind;

    public List<Weather> getWeatherList() {
        return weatherList;
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

}
