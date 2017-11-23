package com.example.openweather.ui.presenter;

import com.example.openweather.api.model.City;
import com.example.openweather.api.model.WeatherResponse;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by kumar on 11/21/17.
 */

public interface MainInteractor {

    Single<WeatherResponse> getWeatherForLatLong(double lat, double longitude, @WeatherResponse.WeatherUnit String weatherUnit);

    Single<WeatherResponse> getWeatherForCity(City city, @WeatherResponse.WeatherUnit String weatherUnit);

    Single<City> getCityByDetail(String cityName, String country);

    List<City> getSimilarCities(String searchString);

}
