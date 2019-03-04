package com.weatherforecastapplication.database.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;

/**
 * This Entity class is used to create a table for City Details.
 *
 * @author Shubham Chauhan
 */
@Entity(tableName = "city_details")
public class CityDetails {

    @PrimaryKey
    @Expose
    public int id;

    @Expose
    public String name;

    @Expose
    public String country;

    public CityDetails(int id, String name, String country) {
        this.id = id;
        this.name = name;
        this.country = country;
    }
}
