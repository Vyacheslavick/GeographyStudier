package com.example.slavick.geographystudier;

import android.app.IntentService;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.annotation.Nullable;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class DataBaseIntentService extends IntentService {

    public DataBaseIntentService() {
        super("DataBaseIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Retrofit.getAll(new Callback<List<Country>>() {
            @Override
            public void success(List<Country> countries, Response response) {
                SovietDataBase db = Room.databaseBuilder(getApplicationContext(), SovietDataBase.class, "countries").build();
                for (int i = 1; i < countries.size(); i++) {
                    CountryDao countryDao = db.getCountryDao();
                    Country country = new Country(i, countries.get(i).name, countries.get(i).capital);
                    countryDao.insert(country);
                }
            }
            @Override
            public void failure(RetrofitError error) {
            }
        });
    }
}
