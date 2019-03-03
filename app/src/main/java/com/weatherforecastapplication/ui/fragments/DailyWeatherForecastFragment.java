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

    private static final String CITY_ID = "cityId";
    private BaseActivity mContext;
    private DailyWeatherForecastViewModel mViewModel;
    private DailyWeatherDetailAdapter mDailyWeatherDetailAdapter;
    private int mCityId;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = (BaseActivity) context;
    }

    public static DailyWeatherForecastFragment getInstance(int cityId) {

        DailyWeatherForecastFragment dailyWeatherForecastFragment = new DailyWeatherForecastFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(CITY_ID, cityId);
        dailyWeatherForecastFragment.setArguments(bundle);
        return dailyWeatherForecastFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            //TODO update mCityId
//            mCityId = getArguments().getInt(CITY_ID);
            mCityId = 1270260;
        }
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
        mViewModel.getDailyWeatherForecastData(mContext, String.valueOf(mCityId)).observe(this, this);
        return view;
    }

    /**
     * Method is used to update weather forecast details according to location.
     *
     * @param cityId cityId
     */
    public void updateDataWithNewLocation(int cityId) {
        mCityId = cityId;
        mViewModel.getDailyWeatherForecastData(mContext, String.valueOf(mCityId));
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
