package com.weatherforecastapplication.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.weatherforecastapplication.R;
import com.weatherforecastapplication.ui.fragments.HourWeatherForecastFragment;

public class WeatherDetailRecyclerViewAdapter extends RecyclerView.Adapter<WeatherDetailRecyclerViewAdapter.ViewHolder> {

    private final HourWeatherForecastFragment hourWeatherForecastFragment;
    private final int sequence;
    private String TAG = this.getClass().getSimpleName();


    public WeatherDetailRecyclerViewAdapter(HourWeatherForecastFragment hourWeatherForecastFragment, int sequence) {
        this.hourWeatherForecastFragment=hourWeatherForecastFragment;
        this.sequence=sequence;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLayoutView;
        if(sequence==1){
            itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.vertical_weather_detail_list_view, null);
        }else{
            itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_weather_detail_list_view, null);
        }

        return new ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

    }

    // Return the size arraylist
    @Override
    public int getItemCount() {
        return 8;
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
        }

    }

}
