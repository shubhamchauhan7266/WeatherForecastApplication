package com.weatherforecastapplication.network;

import com.weatherforecastapplication.constants.ApiConstants;
import com.weatherforecastapplication.database.entity.DailyWeatherForecast;
import com.weatherforecastapplication.database.entity.HourWeatherForecast;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiClientInterface {

    @GET(ApiConstants.WEATHER_HOUR_DETAILS_API)
    Call<HourWeatherForecast> getHourWeatherForecastData(
            @Query(ApiConstants.API_PARAM_CONSTANT.ID_KEY) String id ,
            @Query(ApiConstants.API_PARAM_CONSTANT.APP_ID) String appId
    );

    @GET(ApiConstants.WEATHER_DAILY_DETAILS_API)
    Call<DailyWeatherForecast> getDailyWeatherForecastData(
            @Query(ApiConstants.API_PARAM_CONSTANT.ID_KEY) String id ,
            @Query(ApiConstants.API_PARAM_CONSTANT.APP_ID) String appId
    );
}
