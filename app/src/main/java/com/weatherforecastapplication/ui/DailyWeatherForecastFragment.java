package com.weatherforecastapplication.ui;

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
import com.weatherforecastapplication.models.DailyWeatherForecastResponseModel;

public class DailyWeatherForecastFragment extends Fragment implements Observer<DailyWeatherForecastResponseModel> {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private DailyWeatherForecastViewModel mViewModel;
    private BaseActivity mContext;

    public DailyWeatherForecastFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DailyWeatherForecastFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DailyWeatherForecastFragment newInstance(String param1, String param2) {
        DailyWeatherForecastFragment fragment = new DailyWeatherForecastFragment();
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
            mContext.showProgressDialog();
        }
        mViewModel = ViewModelProviders.of(this).get(DailyWeatherForecastViewModel.class);
        mViewModel.getDailyWeatherForecastData("1270260").observe(this, this);
        return view;
    }

    @Override
    public void onChanged(@Nullable DailyWeatherForecastResponseModel dailyWeatherForecastResponseModel) {

        if (mContext != null && !mContext.isDestroyed() && !mContext.isFinishing() && isAdded()) {

//            if (!ConnectivityUtils.isNetworkEnabled(mContext)) {
//            }
            mContext.removeProgressDialog();
        }
    }
}
