package com.weatherforecastapplication.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.weatherforecastapplication.database.entity.DailyWeatherForecast;

/**
 * This Dao interface is used for define daily weather details operation.
 *
 * @author Shubham Chauhan
 */
@Dao
public interface DailyWeatherForecastDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(DailyWeatherForecast data);

    @Delete
    void delete(DailyWeatherForecast data);

    @Query("SELECT * From daily_weather_forecast WHERE daily_id = 1")
    DailyWeatherForecast getDailyWeatherForecastDetails();
}
