package com.example.openweather.ui.factory;

import com.example.openweather.api.model.ImageResult;
import com.example.openweather.api.model.WeatherResponse;
import com.example.openweather.ui.viewmodel.CityWeatherImageViewModel;

import javax.inject.Inject;

public class ViewModelFactory {
    @Inject
    public ViewModelFactory() {
    }

    public CityWeatherImageViewModel createViewModel(String id, WeatherResponse.Weather weather, CityWeatherImageViewModel.Listener listener) {
        return new CityWeatherImageViewModel(id, weather, listener);
    }
}
