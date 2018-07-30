package com.example.slavick.geographystudier;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;
@Entity
public class Country {
    @PrimaryKey
    public int id;
    public String name;
    @SerializedName("capital")
    public String capital;

    @Ignore
    public Country(String name, String capital) {
        this.name = name;
        this.capital = capital;
    }

    public Country(int id, String name, String capital) {
        this.id = id;
        this.name = name;
        this.capital = capital;
    }
}
