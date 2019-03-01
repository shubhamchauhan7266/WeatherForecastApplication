package com.weatherforecastapplication.database.entity;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;

/**
 * This Entity class is used to create a table for Hourly Weather Forecast Details.
 *
 * @author Shubham Chauhan
 */
@Entity(tableName = "hourly_weather_forecast")
public class HourWeatherForecast {

    @PrimaryKey(autoGenerate = true)
    @Expose
    public int id;

    @Expose
    public double message;

    @Ignore
    @Expose
    public ArrayList<WeatherList> list;

    @Embedded
    @Expose
    public City city;

    public static class City {

        @Expose
        public String country;

        @Expose
        public String name;

        @Expose
        public int id;
    }

    @Entity(foreignKeys = @ForeignKey(entity = HourWeatherForecast.class,
            parentColumns = "id",
            childColumns = "weather_id"))
    public static class WeatherList {

        @PrimaryKey
        @Expose
        public int dt;

        @Embedded
        @Expose
        public Main main;

        @Embedded
        @Expose
        public Wind wind;

        @Expose
        public String dt_txt;

        @Ignore
        @Expose
        public ArrayList<Weather> weather;

        @Expose
        public int weather_id;
    }

    public static class Main {

        @Expose
        public double temp;

        @Expose
        public double temp_min;

        @Expose
        public double temp_max;

        @Expose
        public double pressure;

        @Expose
        public double humidity;
    }

    @Entity(foreignKeys = @ForeignKey(entity = WeatherList.class,
            parentColumns = "dt",
            childColumns = "weather_dt"))
    public static class Weather {

        @PrimaryKey
        @Expose
        public int id;

        @Expose
        public String main;

        @Expose
        public String description;

        @Expose
        public int weather_dt;
    }

    public static class Wind {

        @Expose
        public double speed;
    }
}
