package com.weatherforecastapplication.database.entity;

import android.arch.persistence.room.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This Entity class is used to create a table for Hourly Weather Forecast Details.
 *
 * @author Shubham Chauhan
 */
@Entity(tableName = "hourly_weather_forecast")
public class HourWeatherForecast implements Serializable {
    @SerializedName("cod")
    @Expose
    public String cod;
    @SerializedName("message")
    @Expose
    public Integer message;
    @SerializedName("cnt")
    @Expose
    public Integer cnt;
    @SerializedName("list")
    @Expose
    public ArrayList<WeatherList> list;
    @SerializedName("city")
    @Expose
    public City city;

    public class City {
        @SerializedName("country")
        @Expose
        public String country;
        @SerializedName("coord")
        @Expose
        public Coord coord;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("id")
        @Expose
        public Integer id;
    }

    public class Coord {
        @SerializedName("lat")
        @Expose
        public Integer lat;
        @SerializedName("lon")
        @Expose
        public Integer lon;
    }

    public class WeatherList {
        @SerializedName("dt")
        @Expose
        public Integer dt;
        @SerializedName("main")
        @Expose
        public Main main;
        @SerializedName("clouds")
        @Expose
        public Clouds clouds;
        @SerializedName("wind")
        @Expose
        public Wind wind;
        @SerializedName("sys")
        @Expose
        public Sys sys;
        @SerializedName("dt_txt")
        @Expose
        public String dtTxt;
        @SerializedName("weather")
        @Expose
        public ArrayList<Weather> weather;
    }

    public class Main {
        @SerializedName("temp")
        @Expose
        public Integer temp;
        @SerializedName("temp_min")
        @Expose
        public Integer temp_min;
        @SerializedName("temp_max")
        @Expose
        public Integer temp_max;
        @SerializedName("pressure")
        @Expose
        public Integer pressure;
        @SerializedName("sea_level")
        @Expose
        public Integer sea_level;
        @SerializedName("grnd_level")
        @Expose
        public Integer grnd_level;
        @SerializedName("humidity")
        @Expose
        public Integer humidity;
        @SerializedName("temp_kf")
        @Expose
        public Integer temp_kf;
    }

    public class Weather {
        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("main")
        @Expose
        public String main;
        @SerializedName("description")
        @Expose
        public String description;
        @SerializedName("icon")
        @Expose
        public String icon;
    }

    public class Clouds {
        @SerializedName("all")
        @Expose
        public Integer all;
    }

    public class Wind {
        @SerializedName("speed")
        @Expose
        public Integer speed;
        @SerializedName("deg")
        @Expose
        public Integer deg;
    }

    public class Sys {
        @SerializedName("pod")
        @Expose
        public String pod;
    }
}
