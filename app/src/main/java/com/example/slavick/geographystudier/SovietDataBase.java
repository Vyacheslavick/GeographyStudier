package com.example.slavick.geographystudier;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Country.class}, version = 1)
public abstract class SovietDataBase extends RoomDatabase {
    public abstract CountryDao getCountryDao();
}
