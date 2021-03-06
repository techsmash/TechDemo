package com.example.openweather.ui.presenter;

import com.example.openweather.api.model.City;
import com.example.openweather.api.model.WeatherResponse;

import io.reactivex.Single;

/**
 * Created by kumar on 11/21/17.
 */

public interface MainView  {
    void showLoadingIndicator();
    void hideLoadingIndicator();
    void displayWeatherForCity(WeatherResponse weatherResponse);
    void displayError(Throwable throwable);
    void displayCity(City city);

    void setData(WeatherResponse.Weather weather);
    boolean isDatabaseExists();
}
