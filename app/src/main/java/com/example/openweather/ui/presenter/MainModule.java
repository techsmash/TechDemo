package com.example.openweather.ui.presenter;

import android.content.Context;

import com.example.openweather.R;
import com.example.openweather.api.CityDataSource;
import com.example.openweather.api.LocalFileService;
import com.example.openweather.api.OpenWeatherService;
import com.example.openweather.api.persistance.dao.CityDatabase;
import com.example.openweather.api.persistance.dao.LocalCityDataSource;
import com.example.openweather.injection.ActivityScope;
import com.patloew.rxlocation.RxLocation;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by kumar on 11/21/17.
 */

@Module
public class MainModule {

    private final MainView mainView;

    public MainModule(MainView mainView) {
        this.mainView = mainView;
    }

    @Provides
    @ActivityScope
    MainPresenter provideMainPresenterPresenter(MainInteractor interactor, RxLocation rxLocation) {
        return new MainPresenterImpl(mainView, interactor, rxLocation);
    }


    @Provides
    @ActivityScope
    MainInteractor provideMainInteractor(CityDataSource cityDataSource, OpenWeatherService openWeatherService, LocalFileService localFileService) {
        return new MainInteractorImpl(cityDataSource,openWeatherService, localFileService);
    }


}
