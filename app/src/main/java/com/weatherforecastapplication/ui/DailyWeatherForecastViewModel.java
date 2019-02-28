package com.weatherforecastapplication.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.weatherforecastapplication.WeatherForecastApplication;
import com.weatherforecastapplication.constants.Constants;
import com.weatherforecastapplication.models.DailyWeatherForecastResponseModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DailyWeatherForecastViewModel extends ViewModel {

    //this is the data that we will fetch asynchronously
    private MutableLiveData<DailyWeatherForecastResponseModel> mDailyWeatherForecastData;

    //we will call this method to get the data
    public LiveData<DailyWeatherForecastResponseModel> getDailyWeatherForecastData(String id) {
        //if the list is null
        if (mDailyWeatherForecastData == null) {
            mDailyWeatherForecastData = new MutableLiveData<>();
            //we will load it asynchronously from server in this method
        }

        loadDailyWeatherForecastData(id, Constants.APP_ID);
        //finally we will return the list
        return mDailyWeatherForecastData;
    }


    //This method is using Retrofit to get the JSON data from URL
    private void loadDailyWeatherForecastData(String id, String appId) {

        Call<DailyWeatherForecastResponseModel> call = WeatherForecastApplication.getClient().getDailyWeatherForecastData(id, appId);

        call.enqueue(new Callback<DailyWeatherForecastResponseModel>() {
            @Override
            public void onResponse(Call<DailyWeatherForecastResponseModel> call,
                                   Response<DailyWeatherForecastResponseModel> response) {

                //finally we are setting the list to our MutableLiveData
                mDailyWeatherForecastData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<DailyWeatherForecastResponseModel> call, Throwable t) {

            }
        });
    }
}
