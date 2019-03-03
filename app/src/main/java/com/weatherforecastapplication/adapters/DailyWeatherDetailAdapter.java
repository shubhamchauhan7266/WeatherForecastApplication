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
import com.weatherforecastapplication.database.entity.DailyWeatherForecast;
import com.weatherforecastapplication.utills.DateUtills;

import java.util.ArrayList;

public class DailyWeatherDetailAdapter extends RecyclerView.Adapter<DailyWeatherDetailAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<DailyWeatherForecast.WeatherList> mWeatherList;
    private String TAG = this.getClass().getSimpleName();

    public DailyWeatherDetailAdapter(Context context, ArrayList<DailyWeatherForecast.WeatherList> weatherList) {
        mContext = context;
        mWeatherList = weatherList;
    }

    public void setWeatherList(ArrayList<DailyWeatherForecast.WeatherList> weatherList) {
        mWeatherList = weatherList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.daily_weather_detail_item, parent, false);

        return new ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        DailyWeatherForecast.WeatherList weatherDetails = mWeatherList.get(position);

        switch (weatherDetails.weather.get(0).main) {
            case Constants.RAIN:
                viewHolder.ivClouds.setImageDrawable(mContext.getResources().getDrawable(R.drawable.rain));
                break;
            case Constants.CLOUDS:
                viewHolder.ivClouds.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cloudy));
                break;
            default:
                viewHolder.ivClouds.setImageDrawable(mContext.getResources().getDrawable(R.drawable.sun));
                break;
        }

        viewHolder.tvDate.setText(String.valueOf(DateUtills.getParsedDate(weatherDetails.dt, Constants.DD_MMM_YYYY)));
        viewHolder.tvTemp.setText(String.format(String.valueOf("%.2f " + (char) 0x00B0 + "C"), (weatherDetails.temp.day - 273.15)));
        viewHolder.tvCloudsDes.setText(String.valueOf(weatherDetails.weather.get(0).description));
        viewHolder.tvWindSpeed.setText(String.valueOf(weatherDetails.speed + " m/h"));
        viewHolder.tvPressure.setText(String.valueOf(weatherDetails.pressure + " hpa"));
        viewHolder.tvMaxTemp.setText(String.format(String.valueOf(" Max : %.2f " + (char) 0x00B0 + "C"), (weatherDetails.temp.max - 273.15)));
        viewHolder.tvTempMin.setText(String.format(String.valueOf("Min : %.2f " + (char) 0x00B0 + "C"), (weatherDetails.temp.min - 273.15)));
    }

    @Override
    public int getItemCount() {
        return mWeatherList != null ? mWeatherList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvDate;
        private final TextView tvTemp;
        private final ImageView ivClouds;
        private final TextView tvCloudsDes;
        private final TextView tvWindSpeed;
        private final TextView tvPressure;
        private final TextView tvMaxTemp;
        private final TextView tvTempMin;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvDate = itemView.findViewById(R.id.tv_date);
            tvTemp = itemView.findViewById(R.id.tv_temp);
            ivClouds = itemView.findViewById(R.id.iv_clouds);
            tvCloudsDes = itemView.findViewById(R.id.tv_clouds_des);
            tvWindSpeed = itemView.findViewById(R.id.tv_wind_speed);
            tvPressure = itemView.findViewById(R.id.tv_pressure);
            tvMaxTemp = itemView.findViewById(R.id.tv_max_temp);
            tvTempMin = itemView.findViewById(R.id.tv_state);
        }
    }
}
