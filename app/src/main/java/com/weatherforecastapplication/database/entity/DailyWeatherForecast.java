package com.weatherforecastapplication.database.entity;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.google.gson.annotations.Expose;
import com.weatherforecastapplication.database.converters.DailyWeatherListTypeConverter;
import com.weatherforecastapplication.database.converters.DailyWeatherTypeConverter;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This Entity class is used to create a table for Daily Weather Forecast Details.
 *
 * @author Shubham Chauhan
 */
@Entity(tableName = "daily_weather_forecast")
public class DailyWeatherForecast implements Serializable {

    @PrimaryKey
    @Expose
    public int daily_id = 1;

    @Expose
    public String cod;

    @Expose
    public double message;

    @Expose
    public int cnt;

    @TypeConverters(DailyWeatherListTypeConverter.class)
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

    public static class WeatherList {

        @PrimaryKey
        @Expose
        public int dt;

        @Embedded
        @Expose
        public Temp temp;

        @Expose
        public double pressure;

        @Expose
        public double humidity;

        @Expose
        public double speed;

        @Expose
        public double rain;

        @Expose
        public double clouds;

        @TypeConverters(DailyWeatherTypeConverter.class)
        @Expose
        public ArrayList<Weather> weather;

    }

    public static class Weather {

        @PrimaryKey
        @Expose
        public int id;

        @Expose
        public String main;

        @Expose
        public String description;
    }

    public static class Temp{

        @Expose
        public double day;

        @Expose
        public double min;

        @Expose
        public double max;

        @Expose
        public double night;

        @Expose
        public double eve;

        @Expose
        public double morn;
    }
}
