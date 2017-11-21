package com.example.openweather.api;

import android.content.Context;

import com.example.openweather.R;
import com.example.openweather.api.model.City;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;

/**
 * Created by kumar on 11/21/17.
 */

public class LocalFileService {

    Context context;

    public LocalFileService(Context context) {
        this.context = context;
    }

    public List<City> readJsonStream() throws IOException {
        InputStream in = context.getResources().openRawResource(R.raw.city_list1);
        //InputStream in = new FileInputStream("file:///local/city_list1.json");
        Gson gson = new GsonBuilder().create();
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        List<City> cities = new ArrayList<City>();
        reader.beginArray();
        while (reader.hasNext()) {
            City message = gson.fromJson(reader, City.class);
            cities.add(message);
        }
        reader.endArray();
        reader.close();
        return cities;
    }

    public Flowable<City> readJsonStreamFlowable() {
        return Flowable.create(new FlowableOnSubscribe<City>() {
            @Override
            public void subscribe(final FlowableEmitter<City> theEmitter) throws Exception {
                try {
                    InputStream in = context.getResources().openRawResource(R.raw.city_list);
                    //InputStream in = new FileInputStream("file:///local/city_list1.json");
                    Gson gson = new GsonBuilder().create();
                    JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
                    reader.beginArray();
                    while (reader.hasNext()) {
                        City city = gson.fromJson(reader, City.class);
                        theEmitter.onNext(city);
                    }
                    reader.endArray();
                    reader.close();
                    theEmitter.onComplete();
                } catch (Throwable e) {
                    theEmitter.onError(e);
                }
            }
        }, BackpressureStrategy.BUFFER);
    }
}
