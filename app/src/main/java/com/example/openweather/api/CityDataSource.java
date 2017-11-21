package com.example.openweather.api;

import com.example.openweather.api.model.City;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

/**
 * Interface for the city data store
 */

public interface CityDataSource {

    Single<City> getCityByName(String name);

    void insertCity(List<City> cities);

    void insertCity(City city);


}
