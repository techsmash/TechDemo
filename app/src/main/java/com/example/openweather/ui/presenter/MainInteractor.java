package com.example.openweather.ui.presenter;

import com.example.openweather.api.model.City;
import com.example.openweather.api.model.WeatherResponse;

import io.reactivex.Single;

/**
 * Created by kumar on 11/21/17.
 */

public interface MainInteractor {

    Single<WeatherResponse> getWeatherForLatLong(float lat, float longitude);

    Single<WeatherResponse> getWeatherForCity(City city);

    Single<City> getCityByName(String cityName);

    void loadFromFile();
}
