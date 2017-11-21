package com.example.openweather;

import android.app.Application;

import com.example.openweather.injection.AppComponent;
import com.example.openweather.injection.AppModule;
import com.example.openweather.injection.DaggerAppComponent;
import com.example.openweather.injection.NetworkModule;

public class App extends Application {
    private static App instance;

    public static App getInstance() {
        return instance;
    }

    protected AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule())
                .build();
        appComponent.inject(this);
    }


    public AppComponent getAppComponent() {
        return appComponent;
    }
}
