package com.weatherforecastapplication.database.webrepo;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import com.weatherforecastapplication.database.WeatherForecastDatabase;
import com.weatherforecastapplication.database.dao.DailyWeatherForecastDao;
import com.weatherforecastapplication.database.dao.HourWeatherForecastDao;
import com.weatherforecastapplication.database.entity.DailyWeatherForecast;
import com.weatherforecastapplication.database.entity.HourWeatherForecast;

public class WeatherForecastRepo {

    private final Context mContext;
    private final HourWeatherForecastDao mHourWeatherForecastDao;
    private final DailyWeatherForecastDao mDailyWeatherForecastDao;

    public WeatherForecastRepo(Context context, Application application){
        mContext = context;
        WeatherForecastDatabase database = WeatherForecastDatabase.getInstance(application);
        mHourWeatherForecastDao = database.getHourWeatherForecastDao();
        mDailyWeatherForecastDao = database.getDailyWeatherForecastDao();
    }

    public void insertHourWeatherForecastData(HourWeatherForecast data){
        insertHourTask(data);
    }

    public HourWeatherForecast getHourWeatherForeCastData(){
        return mHourWeatherForecastDao.getHourWeatherForecastDetails();
    }

    @SuppressLint("StaticFieldLeak")
    private void insertHourTask(final HourWeatherForecast data) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                mHourWeatherForecastDao.insert(data);
                return null;
            }
        }.execute();
    }

    public void insertDailyWeatherForecastData(DailyWeatherForecast data){
        insertDailyTask(data);
    }

    public DailyWeatherForecast getDailyWeatherForeCastData(){
        return mDailyWeatherForecastDao.getDailyWeatherForecastDetails();
    }

    @SuppressLint("StaticFieldLeak")
    private void insertDailyTask(final DailyWeatherForecast data) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                mDailyWeatherForecastDao.insert(data);
                return null;
            }
        }.execute();
    }
}