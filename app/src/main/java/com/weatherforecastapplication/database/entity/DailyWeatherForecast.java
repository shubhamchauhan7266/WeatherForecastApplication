package com.weatherforecastapplication.database.entity;

import android.arch.persistence.room.Entity;

import java.io.Serializable;

/**
 * This Entity class is used to create a table for Daily Weather Forecast Details.
 *
 * @author Shubham Chauhan
 */
@Entity(tableName = "daily_weather_forecast")
public class DailyWeatherForecast implements Serializable {
}
