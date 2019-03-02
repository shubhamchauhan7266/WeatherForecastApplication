package com.weatherforecastapplication.database.webrepo;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import com.weatherforecastapplication.database.WeatherForecastDatabase;
import com.weatherforecastapplication.database.dao.HourWeatherForecastDao;
import com.weatherforecastapplication.database.entity.HourWeatherForecast;

public class HourWeatherForecastRepo {

    private final Context mContext;
    private final HourWeatherForecastDao mHourWeatherForecastDao;

    public HourWeatherForecastRepo(Context context, Application application){
        mContext = context;
        WeatherForecastDatabase database = WeatherForecastDatabase.getInstance(application);
        mHourWeatherForecastDao = database.getHourWeatherForecastDao();
    }

    public void insertHourWeatherForecastData(HourWeatherForecast data){
        insertTask(data);
    }

    public HourWeatherForecast getHourWeatherForeCastData(){
        return mHourWeatherForecastDao.getHourWeatherForecastDetails();
    }

    @SuppressLint("StaticFieldLeak")
    private void insertTask(final HourWeatherForecast data) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                mHourWeatherForecastDao.insert(data);
                return null;
            }
        }.execute();
    }
}
