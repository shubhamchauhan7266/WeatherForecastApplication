package com.weatherforecastapplication.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;

import com.weatherforecastapplication.database.entity.HourWeatherForecast;

@Dao
public interface HourWeatherForecastDao {

    @Insert
    void insert(HourWeatherForecast data);

    @Delete
    void delete(HourWeatherForecast data);
}
