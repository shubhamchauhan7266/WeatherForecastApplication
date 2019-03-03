package com.weatherforecastapplication.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.weatherforecastapplication.database.entity.CityDetails;

import java.util.ArrayList;

@Dao
public interface CityDetailsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ArrayList<CityDetails> cityList);

    @Query("SELECT id,name,country From city_details WHERE name = :cityName")
    ArrayList<CityDetails> getCityDetails(String cityName);
}
