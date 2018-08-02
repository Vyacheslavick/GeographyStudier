package com.example.slavick.geographystudier;


import android.app.IntentService;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.UserHandle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements CountryRecyclerAdapter.OnRecyclerItemClick {

    @BindView(R.id.registration)
    TextView registration;

    @BindView(R.id.login)
    TextView login;

    @BindView(R.id.userLogin)
    EditText userLogin;

    @BindView(R.id.password)
    TextView password;

    @BindView(R.id.userPassword)
    EditText userPassword;

    @BindView(R.id.userCountry)
    TextView userCountry;

    @BindView(R.id.logIn)
    Button logIn;

    public String user_name = "File_Name";
    CountryRecyclerAdapter adapter;
    List<Country> countryList;
    SovietDataBase db;
    boolean haveInternet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Intent intent = new Intent(this, DataBaseIntentService.class);
        startService(intent);
        if (haveInternet){
            final RecyclerView list = findViewById(R.id.list);
            LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
            list.setLayoutManager(layoutManager);
            adapter = new CountryRecyclerAdapter(MainActivity.this, db.getCountryDao().getAll(), MainActivity.this);
            list.setAdapter(adapter);
        }
    }


    @OnClick(R.id.logIn)
    public void onClick(){
        try {
            FileInputStream name = openFileInput(user_name.concat(userLogin.getText().toString()));
            FileInputStream pass = openFileInput(userPassword.getText().toString().concat(userLogin.getText().toString()));
            FileInputStream country = openFileInput(userCountry.getText().toString().concat(userLogin.getText().toString()));
            BufferedReader nameReader = new BufferedReader(new InputStreamReader(name));
            BufferedReader passReader = new BufferedReader(new InputStreamReader(pass));
            BufferedReader countryReader = new BufferedReader(new InputStreamReader(country));
            StringBuilder nameBuilder = new StringBuilder();
            StringBuilder passBuilder = new StringBuilder();
            StringBuilder countryBuilder = new StringBuilder();

            String login;

            String password;

            String region;

            while ((login = nameReader.readLine()) != null){
                nameBuilder.append(login);
            }
            while ((password = passReader.readLine()) != null){
                passBuilder.append(password);
            }
            while ((region = countryReader.readLine()) != null){
                countryBuilder.append(region);
            }
            Intent intent = new Intent(this, StartActivity.class);
            startActivity(intent);
        } catch (FileNotFoundException e) {
            login.setText(R.string.user_not_found);
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }




    @OnClick(R.id.registration)
    public void onSecondClick(){
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(int position) {
        userCountry.setText(db.getCountryDao().getAll().get(position).name);
    }
}
