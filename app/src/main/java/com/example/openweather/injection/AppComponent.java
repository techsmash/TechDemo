package com.example.openweather.injection;

import android.content.Context;

import com.example.openweather.App;
import com.example.openweather.api.CityDataSource;
import com.example.openweather.api.LocalFileService;
import com.example.openweather.api.OpenWeatherApi;
import com.example.openweather.api.OpenWeatherService;
import com.example.openweather.service.CreateDatabaseService;
import com.example.openweather.ui.activity.BaseActivity;
import com.patloew.rxlocation.RxLocation;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, NetworkModule.class})
public interface AppComponent {
    void inject(App app);

    void inject(BaseActivity baseActivity);

    void inject(CreateDatabaseService createDatabaseService);

    Context getContext();

    CityDataSource getCityDataSource();

    OpenWeatherService getOpenWeatherService();

    OpenWeatherApi getWeatherApi();

    LocalFileService localFileService();

    RxLocation getRxLocation();


}