package com.weatherforecastapplication.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.weatherforecastapplication.database.entity.HourWeatherForecast;

/**
 * This Dao interface is used for define hourly weather details operation.
 *
 * @author Shubham Chauhan
 */
@Dao
public interface HourWeatherForecastDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(HourWeatherForecast data);

    @Delete
    void delete(HourWeatherForecast data);

    @Query("SELECT * From hourly_weather_forecast WHERE hour_id = 1")
    HourWeatherForecast getHourWeatherForecastDetails();
}
