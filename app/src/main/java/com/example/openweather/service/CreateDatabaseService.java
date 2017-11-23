package com.example.openweather.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.example.openweather.App;
import com.example.openweather.api.CityDataSource;
import com.example.openweather.api.LocalFileService;
import com.example.openweather.ui.presenter.MainInteractor;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by kumar on 11/22/17.
 */

public class CreateDatabaseService extends IntentService {

    Handler handler = new Handler(Looper.getMainLooper());

    @Inject
    LocalFileService localFileService;

    @Inject
    CityDataSource cityDataSource;

    public CreateDatabaseService() {
        super("database service");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        ((App) getApplicationContext()).getAppComponent().inject(this);
        loadFromFile();
    }

    private void loadFromFile() {
        Disposable disposable = localFileService.readJsonStreamFlowable()
                .buffer(5000)
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.computation())
                .subscribe(city -> {
                    Log.d("INSERT...", "...");
                    cityDataSource.insertCity(city);
                }, throwable -> {
                    throwable.getCause().printStackTrace();
                }, () -> {
                    Log.d("INSERT COMPLETE", "...");
                    handler.post(() -> {
                        Toast.makeText(CreateDatabaseService.this, "Database ready", Toast.LENGTH_LONG).show();
                    });
                });
    }

}
