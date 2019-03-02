package com.weatherforecastapplication.ui.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.weatherforecastapplication.BaseActivity;
import com.weatherforecastapplication.R;
import com.weatherforecastapplication.adapters.DailyWeatherDetailAdapter;
import com.weatherforecastapplication.database.entity.DailyWeatherForecast;
import com.weatherforecastapplication.viewmodel.DailyWeatherForecastViewModel;

import java.util.ArrayList;

public class DailyWeatherForecastFragment extends Fragment implements Observer<DailyWeatherForecast> {

    private BaseActivity mContext;
    private DailyWeatherForecastViewModel mViewModel;
    private DailyWeatherDetailAdapter mDailyWeatherDetailAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = (BaseActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_daily_weather_forecast, container, false);

        RecyclerView rvDailyWeatherDetails = view.findViewById(R.id.rv_daily_weather_details);
        rvDailyWeatherDetails.setLayoutManager(new LinearLayoutManager(mContext));
        rvDailyWeatherDetails.setItemAnimator(new DefaultItemAnimator());

        mDailyWeatherDetailAdapter = new DailyWeatherDetailAdapter(mContext, new ArrayList<DailyWeatherForecast.WeatherList>());
        rvDailyWeatherDetails.setAdapter(mDailyWeatherDetailAdapter);

        if (mContext != null && !mContext.isDestroyed() && !mContext.isFinishing() && isAdded()) {

            mContext.showProgressDialog();
        }
        mViewModel = ViewModelProviders.of(this).get(DailyWeatherForecastViewModel.class);
        mViewModel.getDailyWeatherForecastData(mContext, "1270260").observe(this, this);
        return view;
    }

    @Override
    public void onChanged(@Nullable DailyWeatherForecast dailyWeatherForecast) {

        if (mContext != null && !mContext.isDestroyed() && !mContext.isFinishing() && isAdded()) {

            mContext.removeProgressDialog();
        }

        if (dailyWeatherForecast != null && dailyWeatherForecast.list != null && dailyWeatherForecast.list.size() > 0) {
            mDailyWeatherDetailAdapter.setWeatherList(dailyWeatherForecast.list);
            mDailyWeatherDetailAdapter.notifyDataSetChanged();
        }
    }
}
