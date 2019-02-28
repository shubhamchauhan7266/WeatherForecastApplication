package com.weatherforecastapplication.network;

import com.weatherforecastapplication.constants.ApiConstants;
import com.weatherforecastapplication.models.HourWeatherForecastResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiClientInterface {

    @GET(ApiConstants.WEATHER_HOUR_DETAILS_API)
    Call<HourWeatherForecastResponseModel> getHourWeatherForecastData();
}
