package com.example.openweather.api.persistance.dao;

import android.content.Context;

import com.example.openweather.api.CityDataSource;
import com.example.openweather.api.model.City;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

/**
 * Created by kumar on 11/21/17.
 */

public class LocalCityDataSource implements CityDataSource {

    private final CityDao cityDao;
    private final String databaseName;

    public LocalCityDataSource(CityDao cityDao, String databaseName) {
        this.cityDao = cityDao;
        this.databaseName = databaseName;
    }
    @Override
    public Single<City> getCityByName(String name) {
        return cityDao.getCityByName(name);
    }

    @Override
    public void insertCity(List<City> cities) {
        cityDao.insertCity(cities);
    }


    @Override
    public void insertCity(City city) {
        cityDao.insertCity(city);
    }


}
