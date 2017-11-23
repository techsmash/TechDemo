package com.example.openweather.ui.presenter;

import android.util.Log;
import android.widget.Toast;

import com.example.openweather.api.CityDataSource;
import com.example.openweather.api.LocalFileService;
import com.example.openweather.api.OpenWeatherService;
import com.example.openweather.api.model.City;
import com.example.openweather.api.model.WeatherResponse;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by kumar on 11/21/17.
 */

public class MainInteractorImpl implements MainInteractor {

    private CityDataSource cityDataSource;
    private OpenWeatherService openWeatherService;
    LocalFileService localFileService;


    public MainInteractorImpl(CityDataSource cityDataSource, OpenWeatherService openWeatherService, LocalFileService localFileService) {
        this.cityDataSource = cityDataSource;
        this.openWeatherService = openWeatherService;
        this.localFileService = localFileService;

    }

    @Override
    public Single<WeatherResponse> getWeatherForLatLong(double lat, double longitude, String weatherUnit) {
        return openWeatherService.getWeatherForLatLong(lat, longitude, weatherUnit)
                .subscribeOn(Schedulers.computation());
    }

    @Override
    public Single<WeatherResponse> getWeatherForCity(City city, String weatherUnit) {
        return openWeatherService.getWeatherForLatLong(city.coord.lat, city.coord.lon, weatherUnit)
                .subscribeOn(Schedulers.computation());


    }

    @Override
    public Single<City> getCityByDetail(String cityName, String country) {
        if (country == null || country.trim().length() == 0) {
            return cityDataSource.getCityByName(cityName)
                    .subscribeOn(Schedulers.computation());
        } else {
            return cityDataSource.getCityByNameAndCountry(cityName, country)
                    .subscribeOn(Schedulers.computation());
        }
    }

    public List<City> getSimilarCities(String searchString) {
        return cityDataSource.getSimilarCities(searchString);
    }

}
