package com.example.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    private TextView textViewAnswer;
    private Button butShowCheatAns;
    private  String answer;
    public static int cheatCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);
        textViewAnswer = findViewById(R.id.correctAnswer);
        butShowCheatAns = (Button)findViewById(R.id.butShowCheatAns);
        answer = getIntent().getStringExtra(MainActivity.ANS);

        if(savedInstanceState != null) {
            textViewAnswer.setText(savedInstanceState.getString("showAnswer_state"));
            butShowCheatAns.setEnabled(savedInstanceState.getBoolean("butCheat_state"));
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("showAnswer_state", textViewAnswer.getText().toString());
        outState.putBoolean("butCheat_state", butShowCheatAns.isEnabled());
        outState.putString("showAnswer_state", textViewAnswer.getText().toString());
    }

    public void showCorrectAnswer(View view) {
        textViewAnswer.setText(answer);
        butShowCheatAns.setEnabled(false);
        cheatCounter++;
    }
}