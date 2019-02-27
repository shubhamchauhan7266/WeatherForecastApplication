package com.weatherforecastapplication.constants;

/**
 * This interface is used for Api Constant.
 *
 * @author Shubham Chauhan
 */
public interface ApiConstants {

    String BASE_URL = "http://api.openweathermap.org/data/2.5/";
    String WEATHER_HOUR_DETAILS_API = "forecast?id=1270260&APPID=18b9eb4340b018f7bc1b637d5909c7eb";

    interface API_REQUEST_CODE {
        int WEATHER_HOUR_DETAILS_CODE = 1;

    }

    interface API_PARAM_CONSTANT {
        String FORECAST_KEY = "forecast";
        String ID_KEY = "id";
    }

}
