package com.example.slavick.geographystudier;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;
@Entity
public class Country {
    @PrimaryKey
    public long id;

    public String name;

    @SerializedName("capital")
    public String capital;

    public Country(long id, String name, String capital) {
        this.id = id;
        this.name = name;
        this.capital = capital;
    }

    @Override
    public String toString() {
        return "Country{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", capital='" + capital + '\'' +
                '}';
    }
}
