package com.weatherforecastapplication.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;

import com.weatherforecastapplication.database.entity.DailyWeatherForecast;

@Dao
public interface DailyWeatherForecastDao {

    @Insert
    void insert(DailyWeatherForecast data);

    @Delete
    void delete(DailyWeatherForecast data);
}
