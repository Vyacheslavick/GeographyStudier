package com.example.slavick.geographystudier;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import static com.example.slavick.geographystudier.StartActivity.FILE_REGION_NAME;

public class QuestionsActivity extends AppCompatActivity {

    public static final String RIGHT_ANSWER = "Crystal true!";
    public static final String CAPITAL_ASK = "Enter the capital of ";
    List<Country> countryList;
    TextView question;
    EditText answer;
    Button next;
    int randomInt;
    LinearLayout linearLayout;
    int trueAnswer;
    int falseAnswer;
    final SovietDataBase db = Room.databaseBuilder(getApplicationContext(), SovietDataBase.class, "countries").build();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        linearLayout = findViewById(R.id.color);
        question = findViewById(R.id.question);
        answer = findViewById(R.id.answer);
        next = findViewById(R.id.next);
        String region = null;
        try {
            FileInputStream fileInputStream = openFileInput(FILE_REGION_NAME);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream));
            StringBuilder builder = new StringBuilder();
            String string;
            while ((string = reader.readLine()) != null){
                builder.append(string);
            }
            region = builder.toString().toLowerCase();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        randomInt = (int) System.currentTimeMillis() % countryList.size();
        if (randomInt < 0){
            randomInt =- 2*randomInt;
        }

        question.setText(CAPITAL_ASK.concat(db.getCountryDao().getAll().get(randomInt).name));
//        Retrofit.getCountries( region, new Callback<List<Country>>() {
//            @Override
//            public void success(List<Country> countries, Response response) {
//                countryList = countries;
//
//
//                question.setText(CAPITAL_ASK.concat(countryList.get(randomInt).name));
//        }
//
//            @Override
//            public void failure(RetrofitError error) {
//                question.setText("You have not internet");
//            }
//        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String country = db.getCountryDao().getAll().get(randomInt).name;
                question.setText(CAPITAL_ASK.concat(country));
                if (answer.getText().toString().equals(db.getCountryDao().getCountryWithName(country).get(0).name)) {
                    linearLayout.setBackgroundColor(getResources().getColor(R.color.wright));
                    trueAnswer += 1;
                } else {
                    linearLayout.setBackgroundColor(getResources().getColor(R.color.wrong));
                    falseAnswer += 1;
                }
                if (trueAnswer + falseAnswer == 10) {
                    Intent intent = new Intent(QuestionsActivity.this, ResultActivity.class);
                    intent.putExtra(RIGHT_ANSWER, trueAnswer);
                    startActivity(intent);
                } else if (db.getCountryDao().getAll().toArray().length - (randomInt - (trueAnswer + falseAnswer)) > 10) {
                    randomInt += 1;
                    question.setText(CAPITAL_ASK.concat(db.getCountryDao().getAll().get(randomInt).name));
                } else {
                    randomInt -= 1;
                    question.setText(CAPITAL_ASK.concat(db.getCountryDao().getAll().get(randomInt).name));
                }
            }
        });
    }
}
