package com.example.openweather.ui.presenter;

import com.example.openweather.api.model.City;
import com.example.openweather.api.model.WeatherResponse;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by kumar on 11/21/17.
 */

public interface MainPresenter {

    /**
     * Tell the presenter that it's OK to start calling methods on the View at this point.  Should be called from the
     * Fragment's onResume() method.
     */
    void startPresenting();

    /**
     * Tell the presenter that it's no longer safe to call methods on the View at this point.  Should be called from the
     * Fragment's onPause() method.
     */
    void stopPresenting();

    void search(String key);

    void fetchWeather(City city, @WeatherResponse.WeatherUnit String weatherUnit);

    List<City> getSimilarCities(String searchString);

    void onLocationPermissionGranted(@WeatherResponse.WeatherUnit String weatherUnit);

    void stopLocationRequest();

}
