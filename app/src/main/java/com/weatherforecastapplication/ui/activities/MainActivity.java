package com.weatherforecastapplication.ui.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TableLayout;

import com.weatherforecastapplication.BaseActivity;
import com.weatherforecastapplication.R;
import com.weatherforecastapplication.adapters.FragmentViewPagerAdapter;
import com.weatherforecastapplication.ui.fragments.DailyWeatherForecastFragment;
import com.weatherforecastapplication.ui.fragments.HourWeatherForecastFragment;

public class MainActivity extends BaseActivity {

    private FragmentViewPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPagerMirror = findViewById(R.id.view_pager_mirror);
        setUpViewPager(viewPagerMirror);
        TabLayout tabLayout = findViewById(R.id.tab_layout_mirror);
        tabLayout.setupWithViewPager(viewPagerMirror);

        final SearchView searchView= findViewById(R.id.search);
        searchView.setQueryHint("Enter country name");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
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
