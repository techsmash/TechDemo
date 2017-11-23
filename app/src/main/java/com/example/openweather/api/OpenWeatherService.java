package com.example.openweather.api;

import com.example.openweather.api.model.ImageResponse;
import com.example.openweather.api.model.MetadataResponse;
import com.example.openweather.api.model.WeatherResponse;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;

public class OpenWeatherService {
    public static final String FIELDS = "id,title,thumb";
    public static final String SORT_ORDER = "best";

    @Inject
    OpenWeatherApi api;

    @Inject
    public OpenWeatherService() {
    }

    public Call<ImageResponse> searchImages(String phrase) {
        return api.searchImages(phrase, FIELDS, SORT_ORDER);
    }


    public Single<WeatherResponse> getWeatherForLatLong(double lat, double longitude, String weatherUnit) {
        return api.getWeatherForLatLong(lat, longitude, weatherUnit).subscribeOn(Schedulers.io());
    }

    public Single<MetadataResponse> getImageMetadata(String id) {
        return api.getImageMetadata(id).subscribeOn(Schedulers.io());
    }

    public Single<ImageResponse> getSimilarImages(String id) {
        return api.getSimilarImages(id);
    }
}
