package com.example.openweather.ui.presenter;

import android.view.View;

import com.example.openweather.api.CityDataSource;
import com.example.openweather.api.LocalFileService;
import com.example.openweather.api.model.City;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by kumar on 11/21/17.
 */

public class MainPresenterImpl implements MainPresenter {

    //INJECTED
    MainView mainView;

    //INJECTED
    MainInteractor mainInteractor;

    @Inject
    CityDataSource cityDataSource;

    @Inject
    LocalFileService localFileService;

    private CompositeDisposable disposables = new CompositeDisposable();

    public MainPresenterImpl(MainView mainView, MainInteractor mainInteractor) {
        this.mainView = mainView;
        this.mainInteractor = mainInteractor;
    }


    @Override
    public void startPresenting() {
        if(mainView.isDatabaseExists()) {
            return;
        }

        mainInteractor.loadFromFile();


    }

    @Override
    public void stopPresenting() {
        if (disposables != null) {
            disposables.dispose();
        }
    }

    @Override
    public void search(String key) {
        mainView.showLoadingIndicator();
        disposables.add(mainInteractor.getCityByName(key)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mainView::displayCity, mainView::displayError));
    }

    @Override
    public void fetchWeather(City city) {
        mainView.showLoadingIndicator();
        disposables.add(mainInteractor.getWeatherForCity(city)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mainView::displayWeatherForCity, mainView::displayError));
    }
}
