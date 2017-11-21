package com.example.openweather.api.persistance.dao;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.openweather.api.model.City;

/**
 * Created by kumar on 11/21/17.
 */

@Database(entities = City.class, version = 1)
public abstract class CityDatabase extends RoomDatabase {

    public abstract CityDao getCityDao();

    private static volatile CityDatabase INSTANCE;
    private static final String DB_NAME = "cities.db";

    public static CityDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (CityDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            CityDatabase.class, DB_NAME)
                            .build();
                }
            }
        }
        return INSTANCE;
    }



}
