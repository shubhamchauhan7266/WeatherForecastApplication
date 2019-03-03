package com.weatherforecastapplication.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.util.Log;

import com.weatherforecastapplication.BaseActivity;
import com.weatherforecastapplication.WeatherForecastApplication;
import com.weatherforecastapplication.constants.Constants;
import com.weatherforecastapplication.database.entity.HourWeatherForecast;
import com.weatherforecastapplication.database.webrepo.WeatherForecastRepo;
import com.weatherforecastapplication.utills.ConnectivityUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HourWeatherForecastViewModel extends ViewModel {
    private String TAG = HourWeatherForecastViewModel.class.getSimpleName();

    //this is the data that we will fetch asynchronously
    private MutableLiveData<HourWeatherForecast> mHourWeatherForecastData;

    //we will call this method to get the data
    public LiveData<HourWeatherForecast> getHourWeatherForecastData(BaseActivity context, String id) {
        //if the list is null
        if (mHourWeatherForecastData == null) {
            mHourWeatherForecastData = new MutableLiveData<>();
            //we will load it asynchronously from server in this method
        }
        if (ConnectivityUtils.isNetworkEnabled(context)) {
            loadHourWeatherForecastData(context, id, Constants.APP_ID);
        } else {
            WeatherForecastRepo repo = new WeatherForecastRepo(context, context.getApplication());
            HourWeatherForecast hourWeatherForecast = repo.getHourWeatherForeCastData();
            mHourWeatherForecastData.setValue(hourWeatherForecast);
        }

        //finally we will return the list
        return mHourWeatherForecastData;
    }


    //This method is using Retrofit to get the JSON data from URL
    private void loadHourWeatherForecastData(final Context context, String id, String appId) {

        Call<HourWeatherForecast> call = WeatherForecastApplication.getClient().getHourWeatherForecastData(id, appId);

        call.enqueue(new Callback<HourWeatherForecast>() {
            @Override
            public void onResponse(Call<HourWeatherForecast> call,
                                   Response<HourWeatherForecast> response) {
                assert response.body() != null;

                //finally we are setting the list to our MutableLiveData and DB
                WeatherForecastRepo repo = new WeatherForecastRepo(context, ((BaseActivity) context).getApplication());
                repo.insertHourWeatherForecastData(response.body());
                mHourWeatherForecastData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<HourWeatherForecast> call, Throwable throwable) {
                Log.e(TAG, throwable.getMessage());
            }
        });
    }
}
