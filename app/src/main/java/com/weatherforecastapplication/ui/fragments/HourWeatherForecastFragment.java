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
import com.weatherforecastapplication.utills.SharedPrefsUtils;
import com.weatherforecastapplication.viewmodel.HourWeatherForecastViewModel;

import java.util.ArrayList;
import java.util.Objects;

import static com.weatherforecastapplication.constants.Constants.SPF_LOCATION_DATA;
import static com.weatherforecastapplication.constants.Constants.SPK_LOCATION_DATA;

/**
 * This fragment class is used for showing today and tomorrow screen.
 *
 * @author Shubham Chauhan
 */
public class HourWeatherForecastFragment extends Fragment implements Observer<HourWeatherForecast> {

    private static final String DAY_KEY = "day";
    private static final String CITY_ID = "cityId";
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
    private int mCityId;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mContext = (BaseActivity) context;
    }

    /**
     * Method is used get instance of this class
     *
     * @param day day
     * @param cityId cityId
     *
     * @return instance of this class
     */
    public static HourWeatherForecastFragment getInstance(String day, int cityId) {

        HourWeatherForecastFragment hourWeatherForecastFragment = new HourWeatherForecastFragment();
        Bundle bundle = new Bundle();
        bundle.putString(DAY_KEY, day);
        bundle.putInt(CITY_ID, cityId);
        hourWeatherForecastFragment.setArguments(bundle);
        return hourWeatherForecastFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            String day = getArguments().getString(DAY_KEY, Constants.TODAY);

            mCityId = getArguments().getInt(CITY_ID,0);

            if(mCityId == 0){
                mCityId =SharedPrefsUtils.getSharedPrefInt(Objects.requireNonNull(getActivity()),SPF_LOCATION_DATA,SPK_LOCATION_DATA,1270260);
            }

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
        mViewModel.getHourWeatherForecastData(mContext, String.valueOf(mCityId)).observe(this, this);
        return view;
    }

    /**
     * Method is used to init views
     *
     * @param view view
     */
    private void initUi(View view) {
        mTvWindValue = view.findViewById(R.id.tv_wind_value);
        mTvCloudinessValue = view.findViewById(R.id.tv_cloudiness_value);
        mTvPressureValue = view.findViewById(R.id.tv_pressure_value);
        mTvHumidityValue = view.findViewById(R.id.tv_humidity_value);
        mTvState = view.findViewById(R.id.tv_state_name);
        mTvCountry = view.findViewById(R.id.tv_country_name);
    }

    /**
     * Method is used to update weather forecast details according to location.
     *
     * @param day day (Today or Tomorrow)
     * @param cityId cityId
     */
    public void updateDataWithNewLocation(String day, int cityId) {
        mIsTomorrow = day.equals(Constants.TOMORROW);
        mCityId = cityId;
        mViewModel.getHourWeatherForecastData(mContext, String.valueOf(mCityId));
    }

    @Override
    public void onChanged(@Nullable HourWeatherForecast hourWeatherForecast) {

        if (mContext != null && !mContext.isDestroyed() && !mContext.isFinishing() && isAdded()) {

            mContext.removeProgressDialog();
        }

        if (hourWeatherForecast != null && hourWeatherForecast.list != null && hourWeatherForecast.list.size() > 0) {

            long timeStamp = mIsTomorrow ? DateUtills.getNextDayTimeStamp() : DateUtills.getCurrentTimeStamp();
            ArrayList<HourWeatherForecast.WeatherList> list = new ArrayList<>();

            boolean isFirst = true;
            for (HourWeatherForecast.WeatherList weatherList : hourWeatherForecast.list) {
                if (DateUtills.getParsedDate(weatherList.dt, Constants.DD_MMM_YYYY).equals(DateUtills.getParsedDate((timeStamp / 1000), Constants.DD_MMM_YYYY))) {
                    list.add(weatherList);

                    if (isFirst) {

                        isFirst = false;
                        mTvWindValue.setText(String.valueOf(weatherList.wind.speed + " m/h"));
                        mTvCloudinessValue.setText(String.valueOf(weatherList.weather.get(0).description));
                        mTvPressureValue.setText(String.valueOf(weatherList.main.pressure + " hpa"));
                        mTvHumidityValue.setText(String.valueOf(weatherList.main.humidity + " %"));
                        mTvState.setText(hourWeatherForecast.city.name);
                        mTvCountry.setText(hourWeatherForecast.city.country);
                    }
                }
            }
            mHourWeatherDetailAdapter.setWeatherList(list);
            mHourWeatherDetailAdapter.notifyDataSetChanged();
        }
    }
}
