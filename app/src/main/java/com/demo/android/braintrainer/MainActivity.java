package com.demo.android.braintrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView textViewTimer;
    private TextView textViewVariant0;
    private TextView textViewVariant1;
    private TextView textViewVariant2;
    private TextView textViewVariant3;
    private TextView textViewExample;
    private List<TextView> variants;

    private TextView textViewCounter;
    private int result;
    private int countOfAttempts;
    private final String COUNTER_OF_ATTEMPT = "%d / %d";
    boolean timerIsStopped = false;
    private int numberOfRightAnswer;
    private final int MAX_AMOUNT_OF_NUMBERS = 100;
    private final int MAX_AMOUNT_OF_SIGNS = 2;
    private final String EXAMPLE = "%d %s %d =";
    private final String PLUS = "+";
    private final String MINUS = "-";
    private int firstNumber;
    private int secondNumber;
    private int sign;
    private String rightAnswer;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        variants = new ArrayList<>();
        textViewTimer = findViewById(R.id.textViewTimer);
        textViewVariant0 = findViewById(R.id.textViewVariant0);
        textViewVariant1 = findViewById(R.id.textViewVariant1);
        textViewVariant2 = findViewById(R.id.textViewVariant2);
        textViewVariant3 = findViewById(R.id.textViewVariant3);
        textViewCounter = findViewById(R.id.textViewCounter);
        textViewExample = findViewById(R.id.textViewExample);
        variants.add(textViewVariant0);
        variants.add(textViewVariant1);
        variants.add(textViewVariant2);
        variants.add(textViewVariant3);
        toast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
        startTimer();
        playGame();

    }

    public void onClickCheckAnswer(View view) {
        TextView textView = (TextView) view;
        String tag = textView.getTag().toString();
        if (Integer.parseInt(tag) == numberOfRightAnswer) {
            if ( toast.getView().getWindowVisibility() == View.VISIBLE) {
                toast.cancel();
            }
            toast.setText("Верно.");
            toast.show();
            result++;
            countOfAttempts++;
        } else {
            if (toast.getView().getWindowVisibility() == View.VISIBLE) {
                toast.cancel();
            }
            toast.setText("Неправильный ответ.");
            toast.show();
            countOfAttempts++;
        }
        playGame();

    }

    private void playGame(){
        textViewCounter.setText(String.format(COUNTER_OF_ATTEMPT, result, countOfAttempts));
        generateQuestions();
        String newExample;
        if (sign == 0){
            newExample = String.format(EXAMPLE, firstNumber, PLUS, secondNumber);
            textViewExample.setText(newExample);
            rightAnswer = Integer.toString(firstNumber + secondNumber);
        } else {
            newExample = String.format(EXAMPLE, firstNumber, MINUS, secondNumber);
            textViewExample.setText(newExample);
            rightAnswer = Integer.toString(firstNumber - secondNumber);
        }
        for (int i = 0; i < variants.size(); i++) {
            if (i == numberOfRightAnswer){
                variants.get(i).setText(rightAnswer);
            } else {
                int wrongAnswer = generateWrongAnswer();
                variants.get(i).setText(Integer.toString(wrongAnswer));
            }
        }

    }

    private void generateQuestions(){
        numberOfRightAnswer = (int) (Math.random() * variants.size());
        firstNumber = (int) (Math.random() * MAX_AMOUNT_OF_NUMBERS);
        secondNumber = (int) (Math.random() * MAX_AMOUNT_OF_NUMBERS);
        sign = (int) (Math.random() * MAX_AMOUNT_OF_SIGNS);

    }

    private int generateWrongAnswer(){
        int res;
        do {
            res = (int) (Math.random() * MAX_AMOUNT_OF_NUMBERS * 2 + 1) - (MAX_AMOUNT_OF_NUMBERS);
        } while (Integer.parseInt(rightAnswer) == res);
        return res;


    }

    private void startTimer(){

        CountDownTimer timer = new CountDownTimer(20000, 100) {
            @Override
            public void onTick(long l) {
                int seconds = (int) (l / 1000);
                seconds++;
                textViewTimer.setText(Integer.toString(seconds));
                if (seconds < 10) {
                    textViewTimer.setTextColor(getResources().getColor(R.color.colorRed));
                }

            }

            @Override
            public void onFinish() {
                textViewTimer.setText(Integer.toString(0));
                timerIsStopped = true;
                Intent intent = new Intent(getApplicationContext(), FinishGameActivity.class);
                intent.putExtra("result", result);
                startActivity(intent);
                MainActivity.super.finish();
            }
        };
        timer.start();

    }



}
