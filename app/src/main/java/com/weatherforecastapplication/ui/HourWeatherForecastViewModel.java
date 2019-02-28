package com.weatherforecastapplication.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.weatherforecastapplication.WeatherForecastApplication;
import com.weatherforecastapplication.models.HourWeatherForecastResponseModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HourWeatherForecastViewModel extends ViewModel {

    //this is the data that we will fetch asynchronously
    private MutableLiveData<HourWeatherForecastResponseModel> mHourWeatherForecastData;

    //we will call this method to get the data
    public LiveData<HourWeatherForecastResponseModel> getHourWeatherForecastData() {
        //if the list is null
        if (mHourWeatherForecastData == null) {
            mHourWeatherForecastData = new MutableLiveData<>();
            //we will load it asynchronously from server in this method
        }

        loadHourWeatherForecastData();
        //finally we will return the list
        return mHourWeatherForecastData;
    }


    //This method is using Retrofit to get the JSON data from URL
    private void loadHourWeatherForecastData() {

        Call<HourWeatherForecastResponseModel> call = WeatherForecastApplication.getClient().getHourWeatherForecastData();

        call.enqueue(new Callback<HourWeatherForecastResponseModel>() {
            @Override
            public void onResponse(Call<HourWeatherForecastResponseModel> call,
                                   Response<HourWeatherForecastResponseModel> response) {

                //finally we are setting the list to our MutableLiveData
                mHourWeatherForecastData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<HourWeatherForecastResponseModel> call, Throwable t) {

            }
        });
    }
}
