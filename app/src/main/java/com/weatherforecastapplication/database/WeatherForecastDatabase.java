package com.weatherforecastapplication.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.weatherforecastapplication.database.dao.DailyWeatherForecastDao;
import com.weatherforecastapplication.database.dao.HourWeatherForecastDao;
import com.weatherforecastapplication.database.entity.DailyWeatherForecast;
import com.weatherforecastapplication.database.entity.HourWeatherForecast;

/**
 * Class is used to create database for store Entities.
 *
 * @author Shubham Chauhan
 */
@Database(entities = {HourWeatherForecast.class, DailyWeatherForecast.class}, version = 1)
public abstract class WeatherForecastDatabase extends RoomDatabase {

    public abstract HourWeatherForecastDao getHourWeatherForecastDao();
    public abstract DailyWeatherForecastDao getDailyWeatherForecastDao();

    private static WeatherForecastDatabase mProductDatabase;

    public static WeatherForecastDatabase getInstance(final Context context) {
        if (mProductDatabase == null) {
            synchronized (WeatherForecastDatabase.class) {
                if (mProductDatabase == null) {
                    mProductDatabase = Room.databaseBuilder(context.getApplicationContext(),
                            WeatherForecastDatabase.class, "the_archer_database")
                            .build();

                }
            }
        }
        return mProductDatabase;
    }
}
