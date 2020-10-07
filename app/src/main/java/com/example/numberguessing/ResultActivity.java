package com.example.numberguessing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    public static String RESULT = "result text";

    TextView tvResult;

    Button btnGuessAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        String resultText = intent.getStringExtra(RESULT);

        tvResult = (TextView) findViewById(R.id.tvResult);
        tvResult.setText(resultText);

        //put this back to the guessing page
        btnGuessAgain = (Button) findViewById(R.id.btnGuessAgain);
        btnGuessAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, GuessActivity.class);
                startActivity(intent);
            }
        });
    }
}