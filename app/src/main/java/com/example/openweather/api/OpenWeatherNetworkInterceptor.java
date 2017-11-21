package com.example.openweather.api;

import android.content.Context;

import com.example.openweather.R;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class OpenWeatherNetworkInterceptor implements Interceptor {
    @Inject
    Context context;


    @Inject
    public OpenWeatherNetworkInterceptor() {
    }

    //Add the APPID query param to all the requests
    @Override
    public Response intercept(Chain chain) throws IOException {

        String apiKey = context.getString(R.string.open_weather_api_key);

        Request original = chain.request();
        HttpUrl originalHttpUrl = original.url();
        HttpUrl url = originalHttpUrl.newBuilder()
                .addQueryParameter("APPID", apiKey)
                .build();
        Request request = original
                .newBuilder()
                .url(url)
                .build();
        return chain.proceed(request);
    }
}
