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
import android.widget.TextView;

import com.weatherforecastapplication.BaseActivity;
import com.weatherforecastapplication.R;
import com.weatherforecastapplication.adapters.WeatherDetailRecyclerViewAdapter;
import com.weatherforecastapplication.database.entity.HourWeatherForecast;
import com.weatherforecastapplication.viewmodel.HourWeatherForecastViewModel;

import java.util.ArrayList;
import java.util.Objects;

public class HourWeatherForecastFragment extends Fragment implements Observer<HourWeatherForecast> {

    private String TAG = HourWeatherForecastFragment.class.getSimpleName();

    private HourWeatherForecastViewModel mViewModel;
    private BaseActivity mContext;
    private WeatherDetailRecyclerViewAdapter mWeatherDetailRecyclerViewAdapter;
    private TextView mTvWindValue;
    private TextView mTvCloudinessValue;
    private TextView mTvPressureValue;
    private TextView mTvHumidityValue;
    private TextView mTvTempMinValue;
    private TextView mTvTempMaxValue;

    public HourWeatherForecastFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mContext = (BaseActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hour_weather_forecast, container, false);

        initUi(view);

        RecyclerView rvHorizontalWeatherDetails = view.findViewById(R.id.rv_weather_details_horizontal);
        rvHorizontalWeatherDetails.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        rvHorizontalWeatherDetails.setItemAnimator(new DefaultItemAnimator());

        mWeatherDetailRecyclerViewAdapter = new WeatherDetailRecyclerViewAdapter(mContext, new ArrayList<HourWeatherForecast.WeatherList>());
        rvHorizontalWeatherDetails.setAdapter(mWeatherDetailRecyclerViewAdapter);

        if (mContext != null && !mContext.isDestroyed() && !mContext.isFinishing() && isAdded()) {

//            if (!ConnectivityUtils.isNetworkEnabled(mContext)) {
//            }
            mContext.showProgressDialog();
        }
        mViewModel = ViewModelProviders.of(this).get(HourWeatherForecastViewModel.class);
        mViewModel.getHourWeatherForecastData("1270260").observe(this, this);
        return view;
    }

    private void initUi(View view) {
        mTvWindValue = view.findViewById(R.id.tv_wind_value);
        mTvCloudinessValue = view.findViewById(R.id.tv_cloudiness_value);
        mTvPressureValue = view.findViewById(R.id.tv_pressure_value);
        mTvHumidityValue = view.findViewById(R.id.tv_humidity_value);
        mTvTempMinValue = view.findViewById(R.id.tv_temp_min_val);
        mTvTempMaxValue = view.findViewById(R.id.tv_temp_max_val);
    }

    @Override
    public void onChanged(@Nullable HourWeatherForecast hourWeatherForecast) {

        if (mContext != null && !mContext.isDestroyed() && !mContext.isFinishing() && isAdded()) {

//            if (!ConnectivityUtils.isNetworkEnabled(mContext)) {
//            }
            mContext.removeProgressDialog();
        }

        if (hourWeatherForecast != null) {

            mTvWindValue.setText(String.valueOf(hourWeatherForecast.list.get(0).wind.speed + " m/h"));
            mTvCloudinessValue.setText(String.valueOf(hourWeatherForecast.list.get(0).weather.get(0).description));
            mTvPressureValue.setText(String.valueOf(hourWeatherForecast.list.get(0).main.pressure + " hpa"));
            mTvHumidityValue.setText(String.valueOf(hourWeatherForecast.list.get(0).main.humidity + " %"));
            mTvTempMinValue.setText(String.format(String.valueOf("%.2f " + (char) 0x00B0 + "C"), (hourWeatherForecast.list.get(0).main.temp_min - 273.15)));
            mTvTempMaxValue.setText(String.format(String.valueOf("%.2f " + (char) 0x00B0 + "C"), (hourWeatherForecast.list.get(0).main.temp_max - 273.15)));

            mWeatherDetailRecyclerViewAdapter.setWeatherList(Objects.requireNonNull(hourWeatherForecast).list);
            mWeatherDetailRecyclerViewAdapter.notifyDataSetChanged();
        }
    }
}
