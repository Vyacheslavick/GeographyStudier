package com.example.slavick.geographystudier;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StartActivity extends AppCompatActivity implements CountryRecyclerAdapter.OnRecyclerItemClick {

    public static final String FILE_REGION_NAME = "region";
    RegionRecyclerAdapter adapter;
    TextView name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        final RecyclerView list = findViewById(R.id.list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        list.setLayoutManager(layoutManager);
        adapter = new RegionRecyclerAdapter(this, getRegions(), this );
        list.setAdapter(adapter);
    }
    public List<String> getRegions(){
        final List<String> regions = new ArrayList<>();
        regions.add(0, "Africa");
        regions.add(1, "Americas");
        regions.add(2, "Asia");
        regions.add(3, "Europe");
        regions.add(4, "Oceania");
        return regions;
    }

    @Override
    public void onClick(int position) {
        Intent intent = new Intent(this, QuestionsActivity.class);
        try {
            FileOutputStream fileOutputStream = openFileOutput(FILE_REGION_NAME, Context.MODE_PRIVATE);
            fileOutputStream.write(getRegions().get(position).getBytes());
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        startActivity(intent);
    }
}

