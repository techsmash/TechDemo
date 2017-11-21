package com.example.openweather.api.persistance.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.openweather.api.model.City;

import java.util.List;

import io.reactivex.Single;

/**
 * DAO to access the cities from the database
 */

@Dao
public interface CityDao {

    @Query("SELECT * FROM City WHERE lower(name) = lower(:cityName) ")
    Single<City> getCityByName(String cityName);

    @Insert
    void insertCity(List<City> cityList);

    @Insert
    void insertCity(City city);


}
