package com.weatherforecastapplication.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.weatherforecastapplication.BaseActivity;
import com.weatherforecastapplication.WeatherForecastApplication;
import com.weatherforecastapplication.constants.ApiConstants;
import com.weatherforecastapplication.constants.Constants;
import com.weatherforecastapplication.models.WeatherLocationDetailsResponseModel;
import com.weatherforecastapplication.utills.ConnectivityUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherLocationDetailsViewModel extends ViewModel {

    //this is the data that we will fetch asynchronously
    private MutableLiveData<WeatherLocationDetailsResponseModel> mWeatherLocationDetails;

    //we will call this method to get the data
    public LiveData<WeatherLocationDetailsResponseModel> getWeatherLocationDetails(BaseActivity context, double lat,double lon) {
        //if the list is null
        if (mWeatherLocationDetails == null) {
            mWeatherLocationDetails = new MutableLiveData<>();
        }

        if (!ConnectivityUtils.isNetworkEnabled(context)) {

            loadWeatherLocationDetails(context,lat,lon, ApiConstants.API_PARAM_CONSTANT.IMPERIAL,Constants.APP_ID);
        }
        //finally we will return the list
        return mWeatherLocationDetails;
    }


    //This method is using Retrofit to get the JSON data from URL
    private void loadWeatherLocationDetails(final BaseActivity context, double lat, double lon, String units, String appId) {

        Call<WeatherLocationDetailsResponseModel> call = WeatherForecastApplication.getClient().getWeatherLocationDetails(lat, lon,units,appId);

        call.enqueue(new Callback<WeatherLocationDetailsResponseModel>() {
            @Override
            public void onResponse(Call<WeatherLocationDetailsResponseModel> call,
                                   Response<WeatherLocationDetailsResponseModel> response) {

                mWeatherLocationDetails.setValue(response.body());
            }

            @Override
            public void onFailure(Call<WeatherLocationDetailsResponseModel> call, Throwable t) {

            }
        });
    }
}
