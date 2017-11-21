package com.example.openweather.ui.presenter;

import com.example.openweather.api.CityDataSource;
import com.example.openweather.api.LocalFileService;
import com.example.openweather.api.OpenWeatherService;
import com.example.openweather.api.model.City;
import com.example.openweather.api.model.WeatherResponse;

import io.reactivex.Flowable;
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
    public Single<WeatherResponse> getWeatherForLatLong(float lat, float longitude) {
        return openWeatherService.getWeatherForLatLong(lat, longitude)
                .subscribeOn(Schedulers.computation());
    }

    @Override
    public Single<WeatherResponse> getWeatherForCity(City city) {
        return openWeatherService.getWeatherForLatLong(city.coord.lat, city.coord.lon)
                            .subscribeOn(Schedulers.computation());


    }

    @Override
    public Single<City> getCityByName(String cityName) {
        return cityDataSource.getCityByName(cityName)
                .subscribeOn(Schedulers.computation());
    }

    public void loadFromFile() {
        localFileService.readJsonStreamFlowable()
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.computation())
                .subscribe(city->cityDataSource.insertCity(city), throwable -> {throwable.getCause().printStackTrace();});


    }
}
