package com.demo.android.braintrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;

public class FinishGameActivity extends AppCompatActivity {
    private TextView textViewGameResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_game);
        textViewGameResult = findViewById(R.id.textViewGameResult);
        Intent intent = getIntent();
        int result = intent.getIntExtra("result", 0);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int savedResult = preferences.getInt("savedResult", 0);

        textViewGameResult.setText(String.format(getString(R.string.results_of_the_game), result, savedResult));

        if (result > savedResult) {
            preferences.edit().putInt("savedResult", result).apply();
        }
    }

    public void onClickStartNewGame(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        //FinishGameActivity.super.onDestroy();
    }
}
