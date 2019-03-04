package com.weatherforecastapplication.ui.activities;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.weatherforecastapplication.BaseActivity;
import com.weatherforecastapplication.R;
import com.weatherforecastapplication.adapters.CitySearchListAdapter;
import com.weatherforecastapplication.adapters.FragmentViewPagerAdapter;
import com.weatherforecastapplication.constants.Constants;
import com.weatherforecastapplication.database.entity.CityDetails;
import com.weatherforecastapplication.database.webrepo.WeatherForecastRepo;
import com.weatherforecastapplication.models.WeatherLocationDetailsResponseModel;
import com.weatherforecastapplication.ui.fragments.DailyWeatherForecastFragment;
import com.weatherforecastapplication.ui.fragments.HourWeatherForecastFragment;
import com.weatherforecastapplication.utills.AndroidPermissionUtils;
import com.weatherforecastapplication.utills.ConnectivityUtils;
import com.weatherforecastapplication.utills.OtherUtils;
import com.weatherforecastapplication.viewmodel.WeatherLocationDetailsViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements CitySearchListAdapter.ICitySearchListAdapterCallBack,
        View.OnClickListener, Observer<WeatherLocationDetailsResponseModel>, OnSuccessListener<Location> {

    private FragmentViewPagerAdapter mViewPagerAdapter;
    private RecyclerView mRvSearchCity;
    private CitySearchListAdapter mCityListAdapter;
    private ArrayList<CityDetails> mCityList;
    private ViewPager mViewPagerMirror;
    private SearchView mSearchView;
    private TabLayout mTabLayout;
    private int mCurrentCityId;
    private WeatherLocationDetailsViewModel mViewModel;
    private FusedLocationProviderClient mFusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkLocationPermission();

        loadCityDetailsFromAsset();

        initUi();
        setSearchListAdapter();
//        setUpViewPager(mViewPagerMirror);
        setUpSearchView();
    }

    /**
     * Method to check runtime permissions
     */
    public void checkLocationPermission() {
        if (AndroidPermissionUtils.checkPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION)) {

            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, this);

        } else {
            AndroidPermissionUtils.requestForPermission(MainActivity.this,
                    Constants.REQUEST_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION);
        }
    }

    /**
     * Method is used to set up search view.
     */
    private void setUpSearchView() {
//        mSearchView.setQueryHint(getString(R.string.enter_city_name));
        mSearchView.setQueryHint("Enter city name");

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                hideKeyboard();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newQuery) {

                if (newQuery != null && newQuery.length() == 0) {
                    mRvSearchCity.setVisibility(View.GONE);
                } else {
                    WeatherForecastRepo repo = new WeatherForecastRepo(MainActivity.this, getApplication());
                    mCityList = repo.getCityDetails(String.valueOf("%" + newQuery + "%"));
                    mCityListAdapter.setCityList(mCityList);
                    mCityListAdapter.notifyDataSetChanged();
                    mRvSearchCity.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });

        mSearchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSearchView.setIconified(false);
            }
        });

        mSearchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
    }

    /**
     * Method is used to init View.
     */
    private void initUi() {
        mRvSearchCity = findViewById(R.id.rv_search_city);
        mViewPagerMirror = findViewById(R.id.view_pager_mirror);
        mTabLayout = findViewById(R.id.tab_layout_mirror);
        mSearchView = findViewById(R.id.search);
        findViewById(R.id.iv_back).setOnClickListener(this);
    }

    /**
     * Method is used to set City Search List adapter.
     */
    private void setSearchListAdapter() {
        mCityList = new ArrayList<>();
        mCityListAdapter = new CitySearchListAdapter(this, mCityList);
        mRvSearchCity.setLayoutManager(new LinearLayoutManager(this));
        mRvSearchCity.setAdapter(mCityListAdapter);
    }

    /**
     * Method is used to load City details from asset and store into Room Database.
     */
    private void loadCityDetailsFromAsset() {
        try {
            JSONArray jsonArray = new JSONArray(OtherUtils.loadJSONFromAsset(this));

            ArrayList<CityDetails> cityDetailsList = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int id = jsonObject.getInt(Constants.ID);
                String name = jsonObject.getString(Constants.NAME);
                String country = jsonObject.getString(Constants.COUNTRY);
                cityDetailsList.add(new CityDetails(id, name, country));
            }

            WeatherForecastRepo repo = new WeatherForecastRepo(this, getApplication());
            repo.insertCityDetails(cityDetailsList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method is used to set up View Pager.
     *
     * @param viewPagerMirror viewPagerMirror
     */
    private void setUpViewPager(ViewPager viewPagerMirror) {
        mViewPagerAdapter = new FragmentViewPagerAdapter(getSupportFragmentManager());
        mViewPagerAdapter.addFragment(HourWeatherForecastFragment.getInstance(Constants.TODAY, mCurrentCityId), getString(R.string.today));
        mViewPagerAdapter.addFragment(HourWeatherForecastFragment.getInstance(Constants.TOMORROW, mCurrentCityId), getString(R.string.tomorrow));
        mViewPagerAdapter.addFragment(DailyWeatherForecastFragment.getInstance(mCurrentCityId), getString(R.string.next_10_days));

        viewPagerMirror.setOffscreenPageLimit(3);
        viewPagerMirror.setAdapter(mViewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPagerMirror);
    }

    @Override
    public void onCityClick(int position) {
        mSearchView.setQuery("", false);
        mSearchView.clearFocus();
        hideKeyboard();
        mRvSearchCity.setVisibility(View.GONE);

        if (!ConnectivityUtils.isNetworkEnabled(this)) {
            OtherUtils.showAlertDialog(getString(R.string.internet_error_message), getResources().getString(R.string.ok), MainActivity.this, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        }

        int TODAY = 0;
        int TOMORROW = 1;
        int NEXT_7_DAYS = 2;

        // update weather forecast details
        HourWeatherForecastFragment todayFragment = (HourWeatherForecastFragment) mViewPagerAdapter.getItem(TODAY);
        todayFragment.updateDataWithNewLocation(Constants.TODAY, mCityList.get(position).id);

        HourWeatherForecastFragment tomorrowFragment = (HourWeatherForecastFragment) mViewPagerAdapter.getItem(TOMORROW);
        tomorrowFragment.updateDataWithNewLocation(Constants.TOMORROW, mCityList.get(position).id);

        DailyWeatherForecastFragment dailyWeatherForecastFragment = (DailyWeatherForecastFragment) mViewPagerAdapter.getItem(NEXT_7_DAYS);
        dailyWeatherForecastFragment.updateDataWithNewLocation(mCityList.get(position).id);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {

        if (mRvSearchCity.getVisibility() == View.VISIBLE) {
            mRvSearchCity.setVisibility(View.GONE);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case Constants.REQUEST_LOCATION: {
                checkLocationPermission();
                break;
            }
        }
    }

    @Override
    public void onChanged(@Nullable WeatherLocationDetailsResponseModel weatherLocationDetailsResponseModel) {
        Log.e("onChanged", "weatherLocationDetailsResponseModel");

        if (weatherLocationDetailsResponseModel != null) {
            mCurrentCityId = weatherLocationDetailsResponseModel.id;
//            Toast.makeText(this, weatherLocationDetailsResponseModel.name, Toast.LENGTH_SHORT).show();
        }
        setUpViewPager(mViewPagerMirror);
    }

    @Override
    public void onSuccess(Location location) {

        if (AndroidPermissionUtils.checkPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
            Log.e("onConnected", "location inside");

            if (location != null) {
                double lat = location.getLatitude(), lon = location.getLongitude();

                mViewModel = ViewModelProviders.of(this).get(WeatherLocationDetailsViewModel.class);
                mViewModel.getWeatherLocationDetails(this, lat, lon).observe(this, this);
            }
        }
    }
}
