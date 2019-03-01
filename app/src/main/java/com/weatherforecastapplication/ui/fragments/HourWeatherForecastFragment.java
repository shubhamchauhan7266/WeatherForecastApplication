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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.weatherforecastapplication.BaseActivity;
import com.weatherforecastapplication.R;
import com.weatherforecastapplication.adapters.WeatherDetailRecyclerViewAdapter;
import com.weatherforecastapplication.database.entity.HourWeatherForecast;
import com.weatherforecastapplication.viewmodel.HourWeatherForecastViewModel;

public class HourWeatherForecastFragment extends Fragment implements Observer<HourWeatherForecast> {

    private String TAG = HourWeatherForecastFragment.class.getSimpleName();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private HourWeatherForecastViewModel mViewModel;
    private BaseActivity mContext;
    private WeatherDetailRecyclerViewAdapter mWeatherDetailRecyclerViewAdapter;

    public HourWeatherForecastFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HourWeatherForecastFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HourWeatherForecastFragment newInstance(String param1, String param2) {
        HourWeatherForecastFragment fragment = new HourWeatherForecastFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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

        RecyclerView rvHorizontalWeatherDetails = view.findViewById(R.id.rv_weather_details_horizontal);
        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rvHorizontalWeatherDetails.setLayoutManager(layoutManager2);
        rvHorizontalWeatherDetails.setItemAnimator(new DefaultItemAnimator());

        mWeatherDetailRecyclerViewAdapter = new WeatherDetailRecyclerViewAdapter(this,2);
        rvHorizontalWeatherDetails.setAdapter(mWeatherDetailRecyclerViewAdapter);

        if (mContext != null && !mContext.isDestroyed() && !mContext.isFinishing() && isAdded()) {

//            if (!ConnectivityUtils.isNetworkEnabled(mContext)) {
//            }
//            mContext.showProgressDialog();
        }
        mViewModel = ViewModelProviders.of(this).get(HourWeatherForecastViewModel.class);
        mViewModel.getHourWeatherForecastData("1270260").observe(this, this);
        return view;
    }

    @Override
    public void onChanged(@Nullable HourWeatherForecast hourWeatherForecast) {

        Log.e(TAG, hourWeatherForecast.toString());
        if (mContext != null && !mContext.isDestroyed() && !mContext.isFinishing() && isAdded()) {

//            if (!ConnectivityUtils.isNetworkEnabled(mContext)) {
//            }
            mContext.removeProgressDialog();
        }
    }
}
