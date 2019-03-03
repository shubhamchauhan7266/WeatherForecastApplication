package com.weatherforecastapplication.ui.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;

import com.weatherforecastapplication.BaseActivity;
import com.weatherforecastapplication.R;
import com.weatherforecastapplication.adapters.CitySearchListAdapter;
import com.weatherforecastapplication.adapters.FragmentViewPagerAdapter;
import com.weatherforecastapplication.constants.Constants;
import com.weatherforecastapplication.database.entity.CityDetails;
import com.weatherforecastapplication.database.webrepo.WeatherForecastRepo;
import com.weatherforecastapplication.ui.fragments.DailyWeatherForecastFragment;
import com.weatherforecastapplication.ui.fragments.HourWeatherForecastFragment;
import com.weatherforecastapplication.utills.OtherUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements CitySearchListAdapter.ICitySearchListAdapterCallBack {

    private FragmentViewPagerAdapter mViewPagerAdapter;
    private RecyclerView mRvSearchCity;
    private CitySearchListAdapter mCityListAdapter;
    private ArrayList<CityDetails> mCityList;
    private ViewPager mViewPagerMirror;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadCityDetailsFromAsset();

        mRvSearchCity = findViewById(R.id.rv_search_city);
        mViewPagerMirror = findViewById(R.id.view_pager_mirror);
        TabLayout tabLayout = findViewById(R.id.tab_layout_mirror);
        final SearchView searchView = findViewById(R.id.search);

        setSearchListAdapter();
        setUpViewPager(mViewPagerMirror);
        tabLayout.setupWithViewPager(mViewPagerMirror);

        searchView.setQueryHint(getString(R.string.enter_city_name));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
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

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchView.setIconified(false);
            }
        });

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
    }

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
        mViewPagerAdapter.addFragment(HourWeatherForecastFragment.getInstance(Constants.TODAY, 43523), getString(R.string.today));
        mViewPagerAdapter.addFragment(HourWeatherForecastFragment.getInstance(Constants.TOMORROW, 266666), getString(R.string.tomorrow));
        mViewPagerAdapter.addFragment(DailyWeatherForecastFragment.getInstance(9790770), getString(R.string.next_10_days));

        viewPagerMirror.setOffscreenPageLimit(3);
        viewPagerMirror.setAdapter(mViewPagerAdapter);
    }

    @Override
    public void onCityClick(int position) {
        mRvSearchCity.setVisibility(View.GONE);

        int TODAY = 0;
        int TOMORROW = 1;
        int NEXT_7_DAYS = 2;

        HourWeatherForecastFragment todayFragment = (HourWeatherForecastFragment) mViewPagerAdapter.getItem(TODAY);
        todayFragment.updateDataWithNewLocation(Constants.TODAY, mCityList.get(position).id);

        HourWeatherForecastFragment tomorrowFragment = (HourWeatherForecastFragment) mViewPagerAdapter.getItem(TOMORROW);
        tomorrowFragment.updateDataWithNewLocation(Constants.TOMORROW, mCityList.get(position).id);

        DailyWeatherForecastFragment dailyWeatherForecastFragment = (DailyWeatherForecastFragment) mViewPagerAdapter.getItem(NEXT_7_DAYS);
        dailyWeatherForecastFragment.updateDataWithNewLocation(mCityList.get(position).id);
    }
}
