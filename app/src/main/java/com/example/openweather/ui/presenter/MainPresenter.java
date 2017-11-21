package com.example.openweather.ui.presenter;

import com.example.openweather.api.model.City;

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

    void fetchWeather(City city);

}
