package com.example.openweather.api;

import com.example.openweather.api.model.ImageResponse;
import com.example.openweather.api.model.MetadataResponse;
import com.example.openweather.api.model.WeatherResponse;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface OpenWeatherApi {
    @GET("search/images")
    Call<ImageResponse> searchImages(@Query("phrase") String phrase,
                                     @Query("fields") String fields,
                                     @Query("sort_order") String sortOrder);

    @GET("images/{id}")
    Single<MetadataResponse> getImageMetadata(@Path("id") String id);

    @GET("./")
    Single<WeatherResponse> getWeatherForLatLong(@Query("lat") float lat, @Query("lon") float lon);

    @GET("images/{id}/similar")
    Single<ImageResponse> getSimilarImages(@Path("id") String id);

    @GET("./")
    Single<WeatherResponse> getWeatheryByCityName(@Query("q") String name);
}
