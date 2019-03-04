package com.weatherforecastapplication.database.converters;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.weatherforecastapplication.database.entity.DailyWeatherForecast;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * This converter class is used for weather details list to string and vise versa.
 *
 * @author Shubham Chauhan
 */
public class DailyWeatherTypeConverter {

    private static Gson gson = new Gson();
    private static Type type = new TypeToken<ArrayList<DailyWeatherForecast.Weather>>() {
    }.getType();

    @TypeConverter
    public static ArrayList<DailyWeatherForecast.Weather> stringToNestedData(String json) {
        return gson.fromJson(json, type);
    }

    @TypeConverter
    public static String nestedDataToString(ArrayList<DailyWeatherForecast.Weather> nestedData) {
        return gson.toJson(nestedData, type);
    }
}
