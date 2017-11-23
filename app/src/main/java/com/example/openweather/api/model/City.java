package com.example.openweather.api.model;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.StringTokenizer;

/**
 * Created by kumar on 11/21/17.
 */
@Entity(tableName = "City")
public class City {

    @PrimaryKey(autoGenerate = true)
    public long id;

    private static final String DELIMITER = ",";

    public String name;
    public String country;

    public String getCompleteName() {
        return name + DELIMITER + country ;
    }

    public static final String getShortName(String name) {
        if(name == null) {
            return "";
        }
        return new StringTokenizer(name,DELIMITER,false).nextToken();
    }

    public static final String getShortCountry(String key) {
        if(key == null) {
            return "";
        }

        StringTokenizer stringTokenizer =  new StringTokenizer(key,DELIMITER,false);
        if(stringTokenizer.countTokens() > 1) {
            stringTokenizer.nextToken();
            return stringTokenizer.nextToken();
        }
        return "";

    }

    @Embedded
    public Coord coord;


}
