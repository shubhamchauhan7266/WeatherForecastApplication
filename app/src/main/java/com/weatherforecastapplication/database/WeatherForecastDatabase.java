package com.weatherforecastapplication.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.weatherforecastapplication.database.converters.DailyWeatherListTypeConverter;
import com.weatherforecastapplication.database.converters.DailyWeatherTypeConverter;
import com.weatherforecastapplication.database.converters.HourlyWeatherListTypeConverter;
import com.weatherforecastapplication.database.converters.HourlyWeatherTypeConverter;
import com.weatherforecastapplication.database.dao.CityDetailsDao;
import com.weatherforecastapplication.database.dao.DailyWeatherForecastDao;
import com.weatherforecastapplication.database.dao.HourWeatherForecastDao;
import com.weatherforecastapplication.database.entity.CityDetails;
import com.weatherforecastapplication.database.entity.DailyWeatherForecast;
import com.weatherforecastapplication.database.entity.HourWeatherForecast;

/**
 * Class is used to create database for store Entities.
 *
 * @author Shubham Chauhan
 */
@Database(entities = {HourWeatherForecast.class, DailyWeatherForecast.class, CityDetails.class}, version = 1)
@TypeConverters(value = {HourlyWeatherListTypeConverter.class, HourlyWeatherTypeConverter.class,
        DailyWeatherTypeConverter.class, DailyWeatherListTypeConverter.class})
public abstract class WeatherForecastDatabase extends RoomDatabase {

    public abstract HourWeatherForecastDao getHourWeatherForecastDao();

    public abstract DailyWeatherForecastDao getDailyWeatherForecastDao();

    public abstract CityDetailsDao getCityDetailsDao();

    private static WeatherForecastDatabase mProductDatabase;

    public static WeatherForecastDatabase getInstance(final Context context) {
        if (mProductDatabase == null) {
            synchronized (WeatherForecastDatabase.class) {
                if (mProductDatabase == null) {
                    mProductDatabase = Room.databaseBuilder(context.getApplicationContext(),
                            WeatherForecastDatabase.class, "weather_forecast_database")
                            .allowMainThreadQueries()
                            .build();

                }
            }
        }
        return mProductDatabase;
    }
}
