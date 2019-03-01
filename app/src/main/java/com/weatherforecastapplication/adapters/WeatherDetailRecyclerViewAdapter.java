package com.weatherforecastapplication.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.weatherforecastapplication.R;
import com.weatherforecastapplication.constants.Constants;
import com.weatherforecastapplication.database.entity.HourWeatherForecast;

import java.util.ArrayList;

public class WeatherDetailRecyclerViewAdapter extends RecyclerView.Adapter<WeatherDetailRecyclerViewAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<HourWeatherForecast.WeatherList> mWeatherList;
    private String TAG = this.getClass().getSimpleName();


    public WeatherDetailRecyclerViewAdapter(Context context, ArrayList<HourWeatherForecast.WeatherList> weatherList) {
        mContext = context;
        mWeatherList = weatherList;
    }

    public void setWeatherList(ArrayList<HourWeatherForecast.WeatherList> weatherList) {
        mWeatherList = weatherList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_weather_detail_list_view, parent, false);

        return new ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        HourWeatherForecast.WeatherList weather = mWeatherList.get(position);

        switch (weather.weather.get(0).main) {
            case Constants.RAIN:
                viewHolder.ivWeather.setImageDrawable(mContext.getResources().getDrawable(R.drawable.rain));
                break;
            case Constants.CLOUDS:
                viewHolder.ivWeather.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cloudy));
                break;
            default:
                viewHolder.ivWeather.setImageDrawable(mContext.getResources().getDrawable(R.drawable.sun));
                break;
        }

        viewHolder.tvWeatherTemp.setText(String.format(String.valueOf("%.2f " + (char) 0x00B0 + "C"), (weather.main.temp - 273.15)));
        viewHolder.tvWindSpeed.setText(String.valueOf(weather.wind.speed + " m/h"));
        viewHolder.tvPressureValue.setText(String.valueOf(weather.main.pressure + " hpa"));
    }

    @Override
    public int getItemCount() {
        return mWeatherList != null ? mWeatherList.size() : 0;
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView ivWeather;
        private final TextView tvWeatherTemp;
        private final TextView tvWindSpeed;
        private final TextView tvPressureValue;

        ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            ivWeather = itemLayoutView.findViewById(R.id.iv_weather);
            tvWeatherTemp = itemLayoutView.findViewById(R.id.tv_weather_temp);
            tvWindSpeed = itemLayoutView.findViewById(R.id.tv_wind_speed);
            tvPressureValue = itemLayoutView.findViewById(R.id.tv_pressure_value);
        }

    }

}
