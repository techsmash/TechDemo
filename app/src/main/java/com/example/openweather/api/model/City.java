package com.example.openweather.api.model;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by kumar on 11/21/17.
 */
@Entity(tableName = "City")
public class City {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public String name;
    public String country;

    @Embedded
    public Coord coord;


}
