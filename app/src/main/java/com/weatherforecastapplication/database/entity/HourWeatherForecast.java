package com.weatherforecastapplication.database.entity;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.google.gson.annotations.Expose;
import com.weatherforecastapplication.database.converters.HourlyWeatherListTypeConverter;
import com.weatherforecastapplication.database.converters.HourlyWeatherTypeConverter;

import java.util.ArrayList;

/**
 * This Entity class is used to create a table for Hourly Weather Forecast Details.
 *
 * @author Shubham Chauhan
 */
@Entity(tableName = "hourly_weather_forecast")
public class HourWeatherForecast {

    @PrimaryKey()
    @Expose
    public int hour_id = 1;

    @Expose
    public double message;

    @TypeConverters(HourlyWeatherListTypeConverter.class)
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
        public Main main;

        @Embedded
        @Expose
        public Wind wind;

        @Expose
        public String dt_txt;

        @TypeConverters(HourlyWeatherTypeConverter.class)
        @Expose
        public ArrayList<Weather> weather;
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

    public static class Weather {

        @PrimaryKey
        @Expose
        public int id;

        @Expose
        public String main;

        @Expose
        public String description;
    }

    public static class Wind {

        @Expose
        public double speed;
    }
}
