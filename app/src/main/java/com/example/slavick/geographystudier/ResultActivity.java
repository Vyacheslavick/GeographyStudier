package com.example.slavick.geographystudier;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static com.example.slavick.geographystudier.QuestionsActivity.RIGHT_ANSWER;

public class ResultActivity extends AppCompatActivity {

    Button tryAgain;
    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        final Intent intent = new Intent();
        tryAgain = findViewById(R.id.tryAgain);
        result = findViewById(R.id.result);
        if (intent.getIntExtra(RIGHT_ANSWER, 0) <= 3){
            result.setText("Your knowledge in geographic is bad((Рузге чтоли? ");
        } else if (intent.getIntExtra(RIGHT_ANSWER, 0) <= 7 && intent.getIntExtra(RIGHT_ANSWER, 0) > 3) {
            result.setText("Your knowledge in geographic is good)");
        } else {
            result.setText("Your knowledge in geographic is great!))");
        }
        tryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(ResultActivity.this, StartActivity.class);
                startActivity(intent1);
            }
        });
    }
}
