package com.example.slavick.geographystudier;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class RegistrationActivity extends AppCompatActivity implements CountryRecyclerAdapter.OnRecyclerItemClick {

    public String user_name = "File_Name";
    @BindView(R.id.nameCheck)
    TextView nameCheck;
    @BindView(R.id.userName)
    EditText userName;
    @BindView(R.id.passCheck)
    TextView passCheck;
    @BindView(R.id.pass)
    EditText pass;
    @BindView(R.id.passRepeat)
    EditText passRepeat;
    @BindView(R.id.country)
    TextView country;
    @BindView(R.id.create)
    Button create;
    public List<Country> countryList;
    CountryRecyclerAdapter adapter;
    SovietDataBase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                db = Room.databaseBuilder(getApplicationContext(), SovietDataBase.class, "countries").build();
                countryList = db.getCountryDao().getAll();
                final RecyclerView list = findViewById(R.id.countryList);
                LinearLayoutManager layoutManager = new LinearLayoutManager(RegistrationActivity.this);
                list.setLayoutManager(layoutManager);
                adapter = new CountryRecyclerAdapter(RegistrationActivity.this, countryList, RegistrationActivity.this);
                list.setAdapter(adapter);
            }
        }).start();
    }
    @OnClick(R.id.create)
    public void onClick(){
        if (isLoginValid(userName.getText().toString()) && isPasswordValid(pass.getText().toString()) && isCountryChecked(country.getText().toString())){
            try {
                FileOutputStream name = openFileOutput(user_name.concat(userName.getText().toString()), Context.MODE_PRIVATE);
                FileOutputStream password = openFileOutput(pass.getText().toString().concat(userName.getText().toString()), Context.MODE_PRIVATE);
                FileOutputStream countr = openFileOutput(country.getText().toString().concat(userName.getText().toString()), Context.MODE_PRIVATE);
                name.write(userName.getText().toString().getBytes());
                password.write(pass.getText().toString().getBytes());
                countr.write(country.getText().toString().getBytes());
                name.close();
                countr.close();
                password.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            nameCheck.setText("Your name must be longer that 2 letters < without punctuation> ");
            passCheck.setText("Your password must be a bit harder ");
            country.setText("Please, choose your country");
        }
    }

    public boolean isPasswordValid(String password){
        boolean pass = false;
        for (Integer i = 0; i < 10; i++) {
            String number = i.toString();
            if (password.contains(number) && password.replaceAll("qwerty", "").length() > 6 && passRepeat.getText().toString().equals(password)  ){
                pass = true;
                break;
            }
        }
        return pass;
    }
    public boolean isLoginValid(String login){
        boolean log = false;
        if (login.replace(" "  , "").replace("."  , "").replace("!"  , "").replace(","  , "").length() > 2){
            log = true;
        }
        return log;
    }
    public boolean isCountryChecked(String country){
        boolean c = true;
        if (country.equals("Choose your country from list")){
            c = false;
        } else if (country.equals("Please, choose your country")) {
            c = false;
        }
        return c;
    }

    @Override
    public void onClick(int position) {
        country.setText(countryList.get(position).name);
    }
}
