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
import com.weatherforecastapplication.utills.DateUtills;

import java.util.ArrayList;

/**
 * This adapter class is used for Hour weather details.
 *
 * @author Shubham Chauhan
 */
public class HourWeatherDetailAdapter extends RecyclerView.Adapter<HourWeatherDetailAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<HourWeatherForecast.WeatherList> mWeatherList;

    public HourWeatherDetailAdapter(Context context, ArrayList<HourWeatherForecast.WeatherList> weatherList) {
        mContext = context;
        mWeatherList = weatherList;
    }

    /**
     * Method is used to set weather details list
     *
     * @param weatherList a list of weather details
     */
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
        viewHolder.tvTime.setText(String.valueOf(DateUtills.getParsedDate(weather.dt, Constants.HH_A)));
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
        private final TextView tvTime;

        ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            tvTime = itemLayoutView.findViewById(R.id.tv_time);
            ivWeather = itemLayoutView.findViewById(R.id.iv_weather);
            tvWeatherTemp = itemLayoutView.findViewById(R.id.tv_weather_temp);
            tvWindSpeed = itemLayoutView.findViewById(R.id.tv_wind_speed);
            tvPressureValue = itemLayoutView.findViewById(R.id.tv_pressure_value);
        }

    }

}
