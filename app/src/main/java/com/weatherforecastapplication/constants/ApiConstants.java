package com.weatherforecastapplication.constants;

/**
 * This interface is used for Api Constant.
 *
 * @author Shubham Chauhan
 */
public interface ApiConstants {

    String BASE_URL = "http://api.openweathermap.org/data/2.5/";
    String WEATHER_HOUR_DETAILS_API = "forecast";
    String WEATHER_DAILY_DETAILS_API = "forecast/daily";
    String WEATHER_LOCATION_DETAILS_API = "weather";

    interface API_PARAM_CONSTANT {
        String ID_KEY = "id";
        String APP_ID = "appid";
        String LAT = "lat";
        String LON = "lon";
        String UNITS = "units";
        String IMPERIAL = "imperial";
    }
}
