package com.weatherforecastapplication.ui.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.weatherforecastapplication.BaseActivity;
import com.weatherforecastapplication.R;
import com.weatherforecastapplication.database.entity.DailyWeatherForecast;
import com.weatherforecastapplication.viewmodel.DailyWeatherForecastViewModel;

public class DailyWeatherForecastFragment extends Fragment implements Observer<DailyWeatherForecast> {

    private BaseActivity mContext;
    private DailyWeatherForecastViewModel mViewModel;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = (BaseActivity)context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_daily_weather_forecast, container, false);

        if (mContext != null && !mContext.isDestroyed() && !mContext.isFinishing() && isAdded()) {

//            if (!ConnectivityUtils.isNetworkEnabled(mContext)) {
//            }
//            mContext.showProgressDialog();
        }
        mViewModel = ViewModelProviders.of(this).get(DailyWeatherForecastViewModel.class);
        mViewModel.getDailyWeatherForecastData("1270260").observe(this, this);
        return view;
    }

    @Override
    public void onChanged(@Nullable DailyWeatherForecast dailyWeatherForecast) {

        if (mContext != null && !mContext.isDestroyed() && !mContext.isFinishing() && isAdded()) {

//            if (!ConnectivityUtils.isNetworkEnabled(mContext)) {
//            }
            mContext.removeProgressDialog();
        }
    }
}
