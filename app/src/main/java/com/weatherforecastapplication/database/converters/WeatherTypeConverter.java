package com.weatherforecastapplication.database.converters;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.weatherforecastapplication.database.entity.HourWeatherForecast;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class WeatherTypeConverter {

    private static Gson gson = new Gson();
    private static Type type = new TypeToken<ArrayList<HourWeatherForecast.Weather>>() {
    }.getType();

    @TypeConverter
    public static ArrayList<HourWeatherForecast.Weather> stringToNestedData(String json) {
        return gson.fromJson(json, type);
    }

    @TypeConverter
    public static String nestedDataToString(ArrayList<HourWeatherForecast.Weather> nestedData) {
        return gson.toJson(nestedData, type);
    }
}
