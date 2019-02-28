package com.weatherforecastapplication.constants;

/**
 * This interface is used for Api Constant.
 *
 * @author Shubham Chauhan
 */
public interface ApiConstants {

    String BASE_URL = "http://api.openweathermap.org/data/2.5/";
    String WEATHER_HOUR_DETAILS_API = "forecast";
    String WEATHER_DAILY_DETAILS_API = "forecast/daily";   //  f19b9eb5636afa56c3a014cc6fa3a8b8 , 18b9eb4340b018f7bc1b637d5909c7eb

    interface API_REQUEST_CODE {
        int WEATHER_HOUR_DETAILS_CODE = 1;

    }

    interface API_PARAM_CONSTANT {
        String ID_KEY = "id";
        String APP_ID = "appid";
    }

}
