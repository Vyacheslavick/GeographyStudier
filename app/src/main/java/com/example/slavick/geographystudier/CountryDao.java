package com.example.slavick.geographystudier;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface CountryDao {
    @Insert
    public void insertAll(Country... countries);

    @Query("Select * FROM country WHERE name = :name ")
    List<Country> getCountryWithName(String name);

    @Query("SELECT * FROM country")
    List<Country> getAll();
}
