package com.example.numberguessing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class GuessActivity extends AppCompatActivity {

    int number;
    int guessCount = 0;
    int maxChance = 5;


    Button btnCheck;
    Button btnGiveUp;

    TextView hint;
    TextView chance;
    TextView historyTitle;


    //obtain the input
    EditText editTextNumber;
    String hintText = "";
    String chanceLeftText = " chances left";
    boolean startAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess);

        startAgain = false;
        number = new Random().nextInt(101);
        System.out.println("Random number = " + number);

        hint = (TextView) findViewById(R.id.hint);
        chance = (TextView) findViewById(R.id.chance);
        historyTitle = (TextView) findViewById(R.id.historyTitle);

        hint.setVisibility(View.INVISIBLE);

        btnGiveUp = (Button) findViewById(R.id.btnGiveUp);
        btnCheck = (Button) findViewById(R.id.btnCheck);

        editTextNumber = (EditText) findViewById(R.id.editTextNumber);

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String inputString = editTextNumber.getText().toString();
                editTextNumber.setText("");


                //check the number input with the number generated from the system
                if(startAgain) {
                    startActivity(getIntent());
                } else {

                    //if the input is empty, the system will remind the users to type number
                    if (TextUtils.isEmpty(inputString)) {
                        Toast.makeText(GuessActivity.this, "Please enter a Number between 0 and 100!!!", Toast.LENGTH_SHORT).show();
                    } else {
                        int inputNumber = Integer.parseInt(inputString);


                        //if the number not between 0-100
                        if (inputNumber < 0 || inputNumber > 100) {
                            Toast.makeText(GuessActivity.this, "Please enter a Number between 0 and 100!!!", Toast.LENGTH_SHORT).show();
                        } else {
                            guessCount++;


                            //create different situation for users if they are right
                            if (inputNumber == number) {
                                hintText += guessCount + ". Congratulation!!! The number is " + inputNumber + "!!!\n";
                                Intent intent = new Intent(GuessActivity.this, ResultActivity.class);
                                switch (guessCount) {
                                    case 1:
                                        intent.putExtra(ResultActivity.RESULT, "Congratulation!!!\nYou are super lucky!! You got it at your FIRST guess!!! The random number is " + number + "!!");
                                        break;
                                    case 2:
                                        intent.putExtra(ResultActivity.RESULT, "Congratulation!!!\nYou correctly guess the number at your SECOND guess!!! The random number is " + number + "!!");
                                        break;
                                    case 3:
                                        intent.putExtra(ResultActivity.RESULT, "Congratulation!!!\nYou correctly guess the number at your THIRD guess!!! The random number is " + number + "!!");
                                        break;
                                    case 4:
                                        intent.putExtra(ResultActivity.RESULT, "Congratulation!!!\nYou correctly guess the number at your FORTH guess!!! It The random number is " + number + "!!");
                                        break;
                                    case 5:
                                        intent.putExtra(ResultActivity.RESULT, "Congratulation!!!\nFINALLY!!!\nYou correctly guess the number!!! The random number is " + number + "!!");
                                        break;
                                }
                                startActivity(intent);

                                chanceLeftText = "Congratulation!!!\nThe number is " + number +"!!!\nClick the button to start again!!!";
                                chance.setText(chanceLeftText);

                                startAgain = true;
                            } else {

                                //compare the number input with the number generated
                                //the app will give the users feedback of the results (bigger, smaller)
                                if (inputNumber < number) {
                                    hintText += guessCount + ". The random number is bigger than " + inputNumber + ".\n";
                                } else if (inputNumber > number) {
                                    hintText += guessCount + ". The random number is smaller than " + inputNumber + ".\n";
                                }


                                //the situation that users run out of the guessing chances
                                //advise users to start again
                                if (guessCount == 5) {
                                    startAgain = true;
                                    Intent intent = new Intent(GuessActivity.this, ResultActivity.class);
                                    intent.putExtra(ResultActivity.RESULT,"Bad Luck!!\nYou have run out of chance!!\nThe random number is " + number +" .\nGood luck next time.");
                                    startActivity(intent);
                                    chanceLeftText = "You have run out of chance!!!\nClick the button to start again!!!";
                                    chance.setText(chanceLeftText);
                                } else {
                                    chanceLeftText += "!";
                                    chance.setText((maxChance - guessCount) + chanceLeftText);
                                }
                            }

                            hint.setText(hintText);
                            hint.setVisibility(View.VISIBLE);
                            historyTitle.setVisibility(View.VISIBLE);

                            //setting for user to re-start the game
                            if(startAgain){
                                editTextNumber.setVisibility(View.INVISIBLE);
                                btnCheck.setText("START NEW GUESS");
                                btnGiveUp.setVisibility(View.INVISIBLE);
                            }
                        }
                    }
                }
            }
        });


        //provide the give up choice if the user want to quit the game
        btnGiveUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAgain = true;
                Intent intent = new Intent(GuessActivity.this, ResultActivity.class);
                intent.putExtra(ResultActivity.RESULT,"NOOOOOOO!!\nYou choose to GIVE UP!!\nThe random number is " + number +".\nGood luck Next Time.");
                startActivity(intent);
                chanceLeftText = "You had GIVE UP!!!\nClick the button to start again!!!";
                chance.setText(chanceLeftText);

                hintText += "You had GIVE UP!!!\n";
                hint.setText(hintText);
                hint.setVisibility(View.VISIBLE);
                historyTitle.setVisibility(View.VISIBLE);

                editTextNumber.setVisibility(View.INVISIBLE);

                btnCheck.setText("START NEW GUESS");
                btnGiveUp.setVisibility(View.INVISIBLE);
            }
        });
    }
}