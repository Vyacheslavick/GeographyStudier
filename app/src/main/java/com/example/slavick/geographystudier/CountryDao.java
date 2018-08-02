package com.example.slavick.geographystudier;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface CountryDao {
//    @Insert
//    void insertAll(Country... countries);

    @Insert
    void insert(Country country);

    @Query("Select * FROM country WHERE name = :name ")
    List<Country> getCountryWithName(String name);

    @Query("SELECT * FROM country")
    List<Country> getAll();
}
