package com.example.openweather.ui.presenter;

import android.location.Location;
import android.util.Log;

import com.example.openweather.api.CityDataSource;
import com.example.openweather.api.LocalFileService;
import com.example.openweather.api.model.City;
import com.example.openweather.api.model.WeatherResponse;
import com.google.android.gms.location.LocationRequest;
import com.patloew.rxlocation.RxLocation;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by kumar on 11/21/17.
 */

public class MainPresenterImpl implements MainPresenter {

    //INJECTED
    MainView mainView;

    //INJECTED
    MainInteractor mainInteractor;


    //INJECTED
    RxLocation rxLocation;

    Disposable locDisposable;

    private CompositeDisposable disposables = new CompositeDisposable();

    public MainPresenterImpl(MainView mainView, MainInteractor mainInteractor, RxLocation rxLocation) {
        this.mainView = mainView;
        this.mainInteractor = mainInteractor;
        this.rxLocation = rxLocation;
    }


    @Override
    public void startPresenting() {
        if (mainView.isDatabaseExists()) {
            return;
        }

        mainView.startServiceToCreateDB();


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
        String cityName = City.getShortName(key);
        String country = City.getShortCountry(key);
        disposables.add(mainInteractor.getCityByDetail(cityName, country)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mainView::displayCity, mainView::displayError));
    }

    @Override
    public void fetchWeather(City city, String weatherUnit) {
        mainView.showLoadingIndicator();
        disposables.add(mainInteractor.getWeatherForCity(city, weatherUnit)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mainView::displayWeatherForCity, mainView::displayError));
    }

    @Override
    public List<City> getSimilarCities(String searchString) {
        return mainInteractor.getSimilarCities(searchString);
    }

    @SuppressWarnings("MissingPermission")
    @Override
    public void onLocationPermissionGranted(String unit) {
        LocationRequest locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(5000);
        locDisposable = (rxLocation.location().updates(locationRequest).subscribe(
                (location) -> {
                    if (location != null) {
                        disposables.add(mainInteractor.getWeatherForLatLong(location.getLatitude(), location.getLongitude(), unit)
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(mainView::displayWeatherForCity, mainView::displayError));
                    } else {
                        mainView.locationError();
                    }
                }, (throwable -> {
                    Log.d("LOCATION", "error" + throwable.getLocalizedMessage());
                    mainView.locationError();
                })));

    }

    @Override
    public void stopLocationRequest() {
        if (locDisposable != null && !locDisposable.isDisposed()) {
            locDisposable.dispose();
        }
    }
}
