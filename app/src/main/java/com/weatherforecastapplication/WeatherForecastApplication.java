package com.weatherforecastapplication;

import android.support.multidex.MultiDexApplication;

import com.weatherforecastapplication.constants.ApiConstants;
import com.weatherforecastapplication.constants.Constants;
import com.weatherforecastapplication.network.ApiClientInterface;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherForecastApplication extends MultiDexApplication {

    private static Retrofit mRetrofitClient;

    @Override
    public void onCreate() {
        super.onCreate();

        // Init Retrofit CLIENT
        setRetrofitApiClient();
    }

    /**
     * Setup Retropfit CLIENT
     */
    private void setRetrofitApiClient() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .readTimeout(Constants.NETWORK_TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(Constants.NETWORK_TIMEOUT, TimeUnit.SECONDS)
                .build();

        mRetrofitClient = new Retrofit.Builder()
                .baseUrl(ApiConstants.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


    }

    /**
     * Get Retrofit API client
     *
     * @return ApiClientInterface
     */
    public static ApiClientInterface getClient() {
        return mRetrofitClient.create(ApiClientInterface.class);
    }
}
