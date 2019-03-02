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
import com.weatherforecastapplication.adapters.HourWeatherDetailAdapter;
import com.weatherforecastapplication.constants.Constants;
import com.weatherforecastapplication.database.entity.HourWeatherForecast;
import com.weatherforecastapplication.utills.DateUtills;
import com.weatherforecastapplication.viewmodel.HourWeatherForecastViewModel;

import java.util.ArrayList;

public class HourWeatherForecastFragment extends Fragment implements Observer<HourWeatherForecast> {

    private static final String DAY_KEY = "day";
    private String TAG = HourWeatherForecastFragment.class.getSimpleName();

    private HourWeatherForecastViewModel mViewModel;
    private BaseActivity mContext;
    private HourWeatherDetailAdapter mHourWeatherDetailAdapter;
    private TextView mTvWindValue;
    private TextView mTvCloudinessValue;
    private TextView mTvPressureValue;
    private TextView mTvHumidityValue;
    private TextView mTvState;
    private TextView mTvCountry;
    private boolean mIsTomorrow;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mContext = (BaseActivity) context;
    }

    public static HourWeatherForecastFragment getInstance(String day) {

        HourWeatherForecastFragment hourWeatherForecastFragment = new HourWeatherForecastFragment();
        Bundle bundle = new Bundle();
        bundle.putString(DAY_KEY, day);
        hourWeatherForecastFragment.setArguments(bundle);
        return hourWeatherForecastFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            String day = getArguments().getString(DAY_KEY, Constants.TODAY);

            mIsTomorrow = day.equals(Constants.TOMORROW);
        }
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

        mHourWeatherDetailAdapter = new HourWeatherDetailAdapter(mContext, new ArrayList<HourWeatherForecast.WeatherList>());
        rvHorizontalWeatherDetails.setAdapter(mHourWeatherDetailAdapter);

        if (mContext != null && !mContext.isDestroyed() && !mContext.isFinishing() && isAdded()) {

            mContext.showProgressDialog();
        }
        mViewModel = ViewModelProviders.of(this).get(HourWeatherForecastViewModel.class);
        mViewModel.getHourWeatherForecastData(mContext, "1270260").observe(this, this);
        return view;
    }

    private void initUi(View view) {
        mTvWindValue = view.findViewById(R.id.tv_wind_value);
        mTvCloudinessValue = view.findViewById(R.id.tv_cloudiness_value);
        mTvPressureValue = view.findViewById(R.id.tv_pressure_value);
        mTvHumidityValue = view.findViewById(R.id.tv_humidity_value);
        mTvState = view.findViewById(R.id.tv_state_name);
        mTvCountry = view.findViewById(R.id.tv_country_name);
    }

    @Override
    public void onChanged(@Nullable HourWeatherForecast hourWeatherForecast) {

        if (mContext != null && !mContext.isDestroyed() && !mContext.isFinishing() && isAdded()) {

            mContext.removeProgressDialog();
        }

        if (hourWeatherForecast != null && hourWeatherForecast.list != null && hourWeatherForecast.list.size() > 0) {

            mTvWindValue.setText(String.valueOf(hourWeatherForecast.list.get(mIsTomorrow ? 8 : 0).wind.speed + " m/h"));
            mTvCloudinessValue.setText(String.valueOf(hourWeatherForecast.list.get(mIsTomorrow ? 8 : 0).weather.get(0).description));
            mTvPressureValue.setText(String.valueOf(hourWeatherForecast.list.get(mIsTomorrow ? 8 : 0).main.pressure + " hpa"));
            mTvHumidityValue.setText(String.valueOf(hourWeatherForecast.list.get(mIsTomorrow ? 8 : 0).main.humidity + " %"));
            mTvState.setText(hourWeatherForecast.city.name);
            mTvCountry.setText(hourWeatherForecast.city.country);

            long timeStamp = mIsTomorrow ? DateUtills.getNextDayTimeStamp() : DateUtills.getCurrentTimeStamp();
            ArrayList<HourWeatherForecast.WeatherList> list = new ArrayList<>();
            for (HourWeatherForecast.WeatherList weatherList : hourWeatherForecast.list) {
                if (DateUtills.getParsedDate(weatherList.dt, Constants.DD_MMM_YYYY).equals(DateUtills.getParsedDate((timeStamp / 1000), Constants.DD_MMM_YYYY))) {
                    list.add(weatherList);
                }
            }
            mHourWeatherDetailAdapter.setWeatherList(list);
            mHourWeatherDetailAdapter.notifyDataSetChanged();
        }
    }
}
