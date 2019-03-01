package com.weatherforecastapplication.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.weatherforecastapplication.WeatherForecastApplication;
import com.weatherforecastapplication.constants.Constants;
import com.weatherforecastapplication.database.entity.HourWeatherForecast;
import com.weatherforecastapplication.ui.fragments.HourWeatherForecastFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HourWeatherForecastViewModel extends ViewModel {
    private String TAG = HourWeatherForecastViewModel.class.getSimpleName();

    //this is the data that we will fetch asynchronously
    private MutableLiveData<HourWeatherForecast> mHourWeatherForecastData;

    //we will call this method to get the data
    public LiveData<HourWeatherForecast> getHourWeatherForecastData(String id) {
        //if the list is null
        if (mHourWeatherForecastData == null) {
            mHourWeatherForecastData = new MutableLiveData<>();
            //we will load it asynchronously from server in this method
        }

        loadHourWeatherForecastData(id, Constants.APP_ID);
        //finally we will return the list
        return mHourWeatherForecastData;
    }


    //This method is using Retrofit to get the JSON data from URL
    private void loadHourWeatherForecastData(String id, String appId) {

        Call<HourWeatherForecast> call = WeatherForecastApplication.getClient().getHourWeatherForecastData(id, appId);

        call.enqueue(new Callback<HourWeatherForecast>() {
            @Override
            public void onResponse(Call<HourWeatherForecast> call,
                                   Response<HourWeatherForecast> response) {
                assert response.body() != null;
                Log.e(TAG, response.body().city.name);

                //finally we are setting the list to our MutableLiveData
                mHourWeatherForecastData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<HourWeatherForecast> call, Throwable throwable) {
                Log.e(TAG, throwable.getMessage());
            }
        });
    }
}
