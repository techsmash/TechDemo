package com.example.openweather.injection;

import android.app.Application;
import android.content.Context;

import com.example.openweather.R;
import com.example.openweather.api.CityDataSource;
import com.example.openweather.api.LocalFileService;
import com.example.openweather.api.persistance.dao.CityDatabase;
import com.example.openweather.api.persistance.dao.LocalCityDataSource;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.patloew.rxlocation.RxLocation;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    Application application;


    public AppModule(Application application) {
        this.application = application;
    }


    @Provides
    @Singleton
    Context provideContext() {
        return application;
    }


    @Provides
    @Singleton
    CityDataSource provideCityDataSource(Context context) {
        CityDatabase database = CityDatabase.getInstance(context);
        String databaseName = context.getString(R.string.database_name);
        return new LocalCityDataSource(database.getCityDao(), databaseName);
    }


    @Provides
    @Singleton
    LocalFileService provideLocalFileService(Context context) {
        return new LocalFileService(context);
    }

    @Provides
    @Singleton
    static GoogleApiClient provideLocationGoogleApiClient(Context context) {
        return new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API)
                .build();
    }

    @Provides
    @Singleton
    RxLocation provideLocationInteractor(Context context) {
        return new RxLocation(context);
    }

}
