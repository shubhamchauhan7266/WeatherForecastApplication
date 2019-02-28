package com.weatherforecastapplication.ui;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.weatherforecastapplication.BaseActivity;
import com.weatherforecastapplication.R;
import com.weatherforecastapplication.adapters.FragmentViewPagerAdapter;

public class MainActivity extends BaseActivity {

    private FragmentViewPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPagerMirror = findViewById(R.id.view_pager_mirror);
        setUpViewPager(viewPagerMirror);
    }

    /**
     * Method is used to set up View Pager.
     *
     * @param viewPagerMirror viewPagerMirror
     */
    private void setUpViewPager(ViewPager viewPagerMirror) {
        mAdapter = new FragmentViewPagerAdapter(getSupportFragmentManager());
        mAdapter.addFragment(new HourWeatherForecastFragment(), getString(R.string.today));
        mAdapter.addFragment(new HourWeatherForecastFragment(), getString(R.string.tomorrow));
        mAdapter.addFragment(new DailyWeatherForecastFragment(), getString(R.string.next_10_days));

        viewPagerMirror.setAdapter(mAdapter);
    }
}
